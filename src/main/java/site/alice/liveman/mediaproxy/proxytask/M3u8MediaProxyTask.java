/*
 * <Alice LiveMan>
 * Copyright (C) <2018>  <NekoSunflower>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package site.alice.liveman.mediaproxy.proxytask;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import site.alice.liveman.mediaproxy.MediaProxyManager;
import site.alice.liveman.model.ChannelInfo;
import site.alice.liveman.model.VideoInfo;
import site.alice.liveman.utils.HttpRequestUtil;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class M3u8MediaProxyTask extends MediaProxyTask {

    protected static final int                         MAX_RETRY_COUNT = 30;
    private                BlockingDeque<M3u8SeqInfo>  downloadDeque   = new LinkedBlockingDeque<>();
    private                ConcurrentLinkedQueue<File> seqFileQueue    = new ConcurrentLinkedQueue<>();
    protected              AtomicInteger               retryCount      = new AtomicInteger(0);
    private                int                         lastSeqIndex    = 0;
    private final          MediaProxyTask              downloadTask;

    public M3u8MediaProxyTask(String videoId, URI sourceUrl) {
        super(videoId, sourceUrl);
        downloadTask = new MediaProxyTask(getVideoId() + "_DOWNLOAD", null) {
            @Override
            protected void runTask() throws InterruptedException {
                VideoInfo mediaVideoInfo = M3u8MediaProxyTask.this.getVideoInfo();
                log.info("videoId=" + mediaVideoInfo.getVideoUnionId() + ", fps=" + mediaVideoInfo.getFrameRate() + ", resolution=" + mediaVideoInfo.getRealResolution());
                while (retryCount.get() < MAX_RETRY_COUNT) {
                    if (M3u8MediaProxyTask.this.getTerminated()) {
                        return;
                    }
                    M3u8SeqInfo m3u8SeqInfo = downloadDeque.poll(1000, TimeUnit.MILLISECONDS);
                    if (m3u8SeqInfo != null) {
                        if (downloadDeque.size() > 10) {
                            log.warn("警告:节目[" + M3u8MediaProxyTask.this.getVideoId() + "]当前的下载队列长度为:" + downloadDeque.size());
                        }
                        File dictSeqFile = m3u8SeqInfo.getSeqFile();
                        long startTime = System.nanoTime();
                        downloadSeqFile(m3u8SeqInfo);
                        long dt = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
                        if (dt >= 1000) {
                            log.warn("警告:下载[videoId=" + M3u8MediaProxyTask.this.getVideoId() + "]seq文件时间过长[耗时:" + dt + "毫秒, 文件地址:" + m3u8SeqInfo.getSeqUrl() + "]");
                        }
                        while (seqFileQueue.size() > 100) {
                            seqFileQueue.poll();
                        }
                        seqFileQueue.offer(dictSeqFile);
                    }
                }
            }

            @Override
            protected void afterTerminate() {
                M3u8MediaProxyTask.this.terminate();
            }

            @Override
            public String getTempPath() {
                return MediaProxyManager.getTempPath() + "/m3u8/" + M3u8MediaProxyTask.this.getVideoInfo().getVideoUnionId();
            }

            private void downloadSeqFile(M3u8SeqInfo m3u8SeqInfo) {
                for (int i = 0; i < 3; i++) {
                    try {
                        VideoInfo mediaVideoInfo = M3u8MediaProxyTask.this.getVideoInfo();
                        if (!m3u8SeqInfo.getSeqFile().exists()) {
                            if (mediaVideoInfo.getEncodeMethod() == null) {
                                HttpRequestUtil.downloadToFile(m3u8SeqInfo.getSeqUrl(), m3u8SeqInfo.getSeqFile());
                            } else {
                                m3u8SeqInfo.getSeqFile().getParentFile().mkdirs();
                                byte[] encodedData = HttpRequestUtil.downloadUrl(m3u8SeqInfo.getSeqUrl());
                                try {
                                    SecretKeySpec sKeySpec = new SecretKeySpec(mediaVideoInfo.getEncodeKey(), "AES");
                                    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                                    IvParameterSpec ivParameterSpec = new IvParameterSpec(mediaVideoInfo.getEncodeIV());
                                    cipher.init(Cipher.DECRYPT_MODE, sKeySpec, ivParameterSpec);
                                    byte[] decodedData = cipher.doFinal(encodedData);
                                    try (FileOutputStream seqFileStream = new FileOutputStream(m3u8SeqInfo.getSeqFile())) {
                                        IOUtils.write(decodedData, seqFileStream);
                                    }
                                } catch (Throwable e) {
                                    log.warn("媒体数据解密失败{} KEY={},IV={},SEQ={}", e.getMessage(), Hex.encodeHexString(mediaVideoInfo.getEncodeKey()), Hex.encodeHexString(mediaVideoInfo.getEncodeIV()), m3u8SeqInfo.getSeqFile());
                                }
                            }
                            retryCount.set(0);
                        }
                        break;
                    } catch (Throwable e) {
                        if (e instanceof FileNotFoundException) {
                            log.warn(getVideoId() + "出错，媒体文件已过期", e);
                            break;
                        }
                        log.error(getVideoId() + "出错重试(" + retryCount.incrementAndGet() + "/" + MAX_RETRY_COUNT + ")次", e);
                    }
                }
            }
        };
    }

    @Override
    public void afterTerminate() {
        downloadTask.waitForTerminate();
    }

    @Override
    public String getTempPath() {
        return MediaProxyManager.getTempPath() + "/m3u8/" + getVideoInfo().getVideoUnionId();
    }

    @Override
    public void runTask() throws InterruptedException {
        MediaProxyManager.runProxy(downloadTask);
        boolean isFirst = true;
        while (retryCount.get() < MAX_RETRY_COUNT && !getTerminated()) {
            ChannelInfo channelInfo = getVideoInfo().getChannelInfo();
            List<M3u8SeqInfo> tempSeqList = new LinkedList<>();
            long start = System.nanoTime();
            try {
                String m3u8Context = HttpRequestUtil.downloadUrl(getSourceUrl(), Charset.defaultCharset());
                String[] m3u8Lines = m3u8Context.split("\n");
                int seqCount = 0;
                int readSeqCount = 0;
                int startSeq = 0;
                for (String m3u8Line : m3u8Lines) {
                    if (!m3u8Line.startsWith("#") && StringUtils.isNotEmpty(m3u8Line.trim())) {
                        int currentSeqIndex = (startSeq + seqCount);
                        if (currentSeqIndex > lastSeqIndex) {
                            M3u8SeqInfo m3u8SeqInfo = new M3u8SeqInfo();
                            m3u8SeqInfo.setSeqUrl(getSourceUrl().resolve(m3u8Line));
                            m3u8SeqInfo.setSeqFile(new File(getTempPath() + "/" + currentSeqIndex + ".ts"));
                            if (!downloadDeque.contains(m3u8SeqInfo)) {
                                tempSeqList.add(m3u8SeqInfo);
                                lastSeqIndex = currentSeqIndex;
                                readSeqCount++;
                            }
                        }
                        seqCount++;
                    } else {
                        if (m3u8Line.startsWith("#EXT-X-MEDIA-SEQUENCE:")) {
                            startSeq = Integer.parseInt(m3u8Line.split(":")[1]);
                        }
                    }
                }
                if (isFirst && tempSeqList.size() > 3) {
                    tempSeqList = tempSeqList.subList(tempSeqList.size() - 3, tempSeqList.size());
                    isFirst = false;
                }
                for (M3u8SeqInfo m3u8SeqInfo : tempSeqList) {
                    downloadDeque.offer(m3u8SeqInfo);
                }
                if (readSeqCount == 0) {
                    if ((retryCount.incrementAndGet() + 2) % 3 == 0) {
                        log.info(getVideoId() + "没有找到可以下载的片段，重试(" + retryCount.get() + "/" + MAX_RETRY_COUNT + ")次");
                    }
                } else {
                    retryCount.set(0);
                }
            } catch (Throwable e) {
                log.error(getVideoId() + "出错重试(" + retryCount.incrementAndGet() + "/" + MAX_RETRY_COUNT + ")次", e);
            }
            if (channelInfo != null) {
                Long endAt = channelInfo.getEndAt();
                if (endAt != null && endAt < System.currentTimeMillis()) {
                    log.info("节目[" + channelInfo.getChannelName() + "]已到结束时间，结束媒体流下载");
                    break;
                }
            }
            long dt = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
            if (dt >= 1500) {
                log.warn("警告:处理[videoId=" + getVideoInfo().getVideoUnionId() + "]m3u8媒体序列时间过长[耗时:" + dt + "毫秒, 文件地址:" + getSourceUrl() + "]");
            }
            Thread.sleep(Math.max(500 - dt, 0));
        }
    }

    class M3u8SeqInfo {
        private URI  seqUrl;
        private File seqFile;
        private Long convertPid;

        public URI getSeqUrl() {
            return seqUrl;
        }

        public void setSeqUrl(URI seqUrl) {
            this.seqUrl = seqUrl;
        }

        public File getSeqFile() {
            return seqFile;
        }

        public void setSeqFile(File seqFile) {
            this.seqFile = seqFile;
        }

        public Long getConvertPid() {
            return convertPid;
        }

        public void setConvertPid(Long convertPid) {
            this.convertPid = convertPid;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            M3u8SeqInfo that = (M3u8SeqInfo) o;
            return Objects.equals(seqFile, that.seqFile);
        }

        @Override
        public int hashCode() {
            return Objects.hash(seqFile);
        }
    }

    public String createM3U8File() {
        VideoInfo videoInfo = getVideoInfo();
        StringBuilder sb = new StringBuilder();
        if (seqFileQueue.isEmpty()) {
            log.warn("节目[" + videoInfo.getVideoUnionId() + "]的m3u8序列为空!");
        } else {
            for (File seqFile : seqFileQueue) {
                if (sb.length() == 0) {
                    sb.append("#EXTM3U\n" +
                            "#EXT-X-VERSION:3\n" +
                            "#EXT-X-TARGETDURATION:2\n" +
                            "#EXT-X-MEDIA-SEQUENCE:" + Integer.parseInt(FilenameUtils.getBaseName(seqFile.getName())) + "\n" +
                            "#EXT-X-DISCONTINUITY-SEQUENCE:1\n");
                }
                sb.append("#EXTINF:1.0,\n");
                sb.append("/mediaProxy/temp/m3u8/" + videoInfo.getVideoUnionId() + "/" + seqFile.getName()).append("\n");
            }
        }
        return sb.toString();
    }
}
