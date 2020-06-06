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

package site.alice.liveman.web.rpc;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.alice.liveman.config.SettingConfig;
import site.alice.liveman.model.AccountInfo;
import site.alice.liveman.model.BillRecord;
import site.alice.liveman.model.LiveManSetting;
import site.alice.liveman.service.broadcast.BroadcastTask;
import site.alice.liveman.utils.SecurityUtils;
import site.alice.liveman.web.dataobject.ActionResult;
import site.alice.liveman.web.dataobject.vo.AccountInfoVO;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

@Slf4j
@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private LiveManSetting liveManSetting;
    @Autowired
    private SettingConfig  settingConfig;
    @Autowired
    private HttpSession    session;

    @RequestMapping("/accountList.json")
    public ActionResult<List<AccountInfoVO>> accountList() {
        AccountInfo currentAccount = (AccountInfo) session.getAttribute("account");
        if (!currentAccount.isAdmin()) {
            return ActionResult.getErrorResult("权限不足");
        }
        Set<AccountInfo> accounts = liveManSetting.getAccounts();
        List<AccountInfoVO> accountInfoVOList = new ArrayList<>();
        for (AccountInfo account : accounts) {
            AccountInfoVO accountInfoVO = new AccountInfoVO();
            BeanUtils.copyProperties(account, accountInfoVO);
            // 如果不是管理员，不展示其他账号的AP点数
            if (!currentAccount.isAdmin()) {
                accountInfoVO.setPoint(-1);
            }
            // 查找该账号下的所有共享号
            List<AccountInfoVO> shardAccounts = new ArrayList<>();
            for (AccountInfo shardAccountInfo : accounts) {
                if (account.getAccountId().equals(shardAccountInfo.getParentAccountId())) {
                    AccountInfoVO accountVO = new AccountInfoVO();
                    accountVO.setAccountId(shardAccountInfo.getAccountId());
                    accountVO.setNickname(shardAccountInfo.getNickname());
                    accountVO.setAccountSite(shardAccountInfo.getAccountSite());
                    shardAccounts.add(accountVO);
                }
            }
            accountInfoVO.setShardAccounts(shardAccounts);
            accountInfoVOList.add(accountInfoVO);
        }
        return ActionResult.getSuccessResult(accountInfoVOList);
    }

    @RequestMapping("/billList.json")
    public ActionResult<List<BillRecord>> billList(String accountId) {
        AccountInfo account = (AccountInfo) session.getAttribute("account");
        if (account.isAdmin()) {
            if (StringUtils.isNotEmpty(accountId)) {
                AccountInfo byAccountId = liveManSetting.findByAccountId(accountId);
                if (byAccountId != null) {
                    return ActionResult.getSuccessResult(byAccountId.getBillRecords());
                } else {
                    return ActionResult.getErrorResult("找不到指定账户[" + accountId + "]的用户信息");
                }
            }
        }
        return ActionResult.getSuccessResult(account.getBillRecords());
    }

    @RequestMapping("/info.json")
    public ActionResult info() {
        AccountInfo account = (AccountInfo) session.getAttribute("account");
        AccountInfoVO accountInfoVO = new AccountInfoVO();
        BeanUtils.copyProperties(account, accountInfoVO);
        if (liveManSetting.findByAccountId(account.getAccountId()) != null) {
            accountInfoVO.setSaved(true);
        }
        accountInfoVO.setBillTimeMap(new HashMap<>(account.getBillTimeMap()));
        accountInfoVO.setServerPoints(liveManSetting.getServerPoints());
        // 查找该账号下的所有共享号
        CopyOnWriteArraySet<AccountInfo> accounts = liveManSetting.getAccounts();
        List<AccountInfoVO> shardAccounts = new ArrayList<>();
        for (AccountInfo shardAccountInfo : accounts) {
            if (account.getAccountId().equals(shardAccountInfo.getParentAccountId())) {
                AccountInfoVO accountVO = new AccountInfoVO();
                accountVO.setAccountId(shardAccountInfo.getAccountId());
                accountVO.setNickname(shardAccountInfo.getNickname());
                accountVO.setAccountSite(shardAccountInfo.getAccountSite());
                shardAccounts.add(accountVO);
            }
        }
        accountInfoVO.setShardAccounts(shardAccounts);
        AccountInfo parentAccountInfo = account.getParentAccountInfo();
        if (parentAccountInfo != null) {
            accountInfoVO.setParentAccountName(parentAccountInfo.getNickname());
        }
        return ActionResult.getSuccessResult(accountInfoVO);
    }

    @RequestMapping("/addAccount.json")
    public ActionResult addAccount(@RequestBody AccountInfoVO accountInfoVO) {
        AccountInfo account = (AccountInfo) session.getAttribute("account");
        if (account.getAccountSite().equals("17live")) {
            return ActionResult.getErrorResult("暂不支持保存17Live账号，敬请谅解！");
        }
        AccountInfo byAccountId = liveManSetting.findByAccountId(account.getAccountId());
        if (byAccountId != null) {
            return ActionResult.getErrorResult("此账号已存在，如要更新账号信息请删除后重新添加");
        }
        account.setDescription(accountInfoVO.getDescription());
        Set<AccountInfo> accounts = liveManSetting.getAccounts();
        if (!accounts.add(account)) {
            return ActionResult.getErrorResult("此账号已存在，如要更新账号信息请删除后重新添加");
        }
        try {
            settingConfig.saveSetting(liveManSetting);
        } catch (Exception e) {
            log.error("保存系统配置信息失败", e);
            return ActionResult.getErrorResult("系统内部错误，请联系管理员");
        }
        return ActionResult.getSuccessResult(null);
    }

    @RequestMapping("/createShareCode.json")
    public ActionResult createShareCode() {
        AccountInfo account = (AccountInfo) session.getAttribute("account");
        AccountInfo byAccountId = liveManSetting.findByAccountId(account.getAccountId());
        byAccountId.setShareCode(UUID.randomUUID().toString());
        try {
            settingConfig.saveSetting(liveManSetting);
        } catch (Exception e) {
            log.error("保存系统配置信息失败", e);
            return ActionResult.getErrorResult("系统内部错误，请联系管理员");
        }
        return ActionResult.getSuccessResult(byAccountId.getShareCode());
    }

    @RequestMapping("/bindShareCode.json")
    public ActionResult bindShareCode(String shareCode) {
        AccountInfo account = (AccountInfo) session.getAttribute("account");
        if (account.getParentAccountId() != null) {
            return ActionResult.getErrorResult("该账号已绑定其他父账号，请先解除绑定！");
        }
        if (account.getPoint() != 0) {
            return ActionResult.getErrorResult("账户剩余AP点数不为0，无法绑定父账号！");
        }
        if (shareCode == null) {
            return ActionResult.getErrorResult("共享码不存在！");
        }
        CopyOnWriteArraySet<AccountInfo> accounts = liveManSetting.getAccounts();
        AccountInfo byAccountId = null;
        for (AccountInfo accountInfo : accounts) {
            if (account.getAccountId().equals(accountInfo.getParentAccountId())) {
                return ActionResult.getErrorResult("该账号已被其他子账号绑定，请先解除绑定！");
            }
        }
        for (AccountInfo accountInfo : accounts) {
            if (shareCode.equals(accountInfo.getShareCode())) {
                if (accountInfo.getParentAccountId() != null) {
                    return ActionResult.getErrorResult("尝试绑定的账号已绑定其他父账号，无法完成绑定操作！");
                }
                byAccountId = accountInfo;
            }
        }
        if (byAccountId == null) {
            return ActionResult.getErrorResult("共享码不存在！");
        }
        if (account.getAccountId().equals(byAccountId.getAccountId())) {
            return ActionResult.getErrorResult("不能和自己建立绑定关系！");
        }
        log.info("账号[" + account.getAccountId() + "]与父账号[" + byAccountId.getAccountId() + "]建立绑定关系");
        account.setParentAccountId(byAccountId.getAccountId());
        account.setParentAccountInfo(byAccountId);
        try {
            settingConfig.saveSetting(liveManSetting);
        } catch (Exception e) {
            log.error("保存系统配置信息失败", e);
            account.setParentAccountId(null);
            account.setParentAccountInfo(null);
            return ActionResult.getErrorResult("系统内部错误，请联系管理员");
        }
        return ActionResult.getSuccessResult(null);
    }

    @RequestMapping("/unbindParent.json")
    public ActionResult unbind() {
        AccountInfo account = (AccountInfo) session.getAttribute("account");
        if (account.getParentAccountId() != null) {
            log.info(account.getAccountId() + "解除了与账号[" + account.getParentAccountId() + "]的共享关系");
            account.setParentAccountId(null);
            account.setParentAccountInfo(null);
            try {
                settingConfig.saveSetting(liveManSetting);
            } catch (Exception e) {
                log.error("保存系统配置信息失败", e);
                return ActionResult.getErrorResult("系统内部错误，请联系管理员");
            }
        }
        return ActionResult.getSuccessResult(null);
    }

    @RequestMapping("/unbindSubAccount.json")
    public ActionResult unbindSubAccount(String accountId) {
        AccountInfo account = (AccountInfo) session.getAttribute("account");
        AccountInfo byAccountId = liveManSetting.findByAccountId(accountId);
        if (byAccountId != null && account.getAccountId().equals(byAccountId.getParentAccountId())) {
            log.info(account.getAccountId() + "解除了与账号[" + accountId + "]的共享关系");
            byAccountId.setParentAccountId(null);
            byAccountId.setParentAccountInfo(null);
            try {
                settingConfig.saveSetting(liveManSetting);
            } catch (Exception e) {
                log.error("保存系统配置信息失败", e);
                return ActionResult.getErrorResult("系统内部错误，请联系管理员");
            }
        }
        return ActionResult.getSuccessResult(null);
    }

    @RequestMapping("/removeAccount.json")
    public ActionResult removeAccount(String accountId) {
        AccountInfo account = (AccountInfo) session.getAttribute("account");
        AccountInfo byAccountId = liveManSetting.findByAccountId(accountId);
        if (byAccountId != null) {
            if (byAccountId.getAccountId().equals(account.getAccountId()) || account.isAdmin()) {
                if (byAccountId.getPoint() != 0) {
                    return ActionResult.getErrorResult("账户AP点数非零，无法删除！");
                }
                if (byAccountId.getCurrentVideo() != null) {
                    BroadcastTask broadcastTask = byAccountId.getCurrentVideo().getBroadcastTask();
                    if (broadcastTask != null) {
                        if (!broadcastTask.terminateTask()) {
                            log.info("删除账户信息失败：无法终止转播任务，CAS操作失败");
                            return ActionResult.getErrorResult("删除账户信息失败：无法终止转播任务，请稍后重试");
                        }
                    }
                }
            } else {
                return ActionResult.getErrorResult("您没有权限删除他人账户信息");
            }
        } else {
            return ActionResult.getErrorResult("此账户已被删除，请刷新页面后重试");
        }
        liveManSetting.getAccounts().remove(byAccountId);
        try {
            settingConfig.saveSetting(liveManSetting);
        } catch (Exception e) {
            log.error("保存系统配置信息失败", e);
            return ActionResult.getErrorResult("系统内部错误，请联系管理员");
        }
        return ActionResult.getSuccessResult(null);
    }

    @RequestMapping("/useCard.json")
    public synchronized ActionResult<Integer> useCard(String cards) {
        AccountInfo account = (AccountInfo) session.getAttribute("account");
        if (liveManSetting.findByAccountId(account.getAccountId()) == null) {
            return ActionResult.getErrorResult("请先前往[我的账户]菜单保存账号!");
        }
        if (account.getParentAccountId() != null) {
            return ActionResult.getErrorResult("该账号已绑定其他父账号，请先解除绑定！");
        }
        int totalPoint = 0;
        try {
            File usedcardFile = new File("usedcard.txt");
            List<String> usedCardLine = Collections.emptyList();
            if (usedcardFile.exists()) {
                usedCardLine = IOUtils.readLines(new FileInputStream(usedcardFile), "utf-8");
            } else {
                usedcardFile.createNewFile();
            }
            Set<String> cardLines = new HashSet<>(Arrays.asList(cards.split("\n")));
            for (String cardLine : cardLines) {
                cardLine = cardLine.trim();
                if (StringUtils.isNotEmpty(cardLine) && !usedCardLine.contains(cardLine)) {
                    if (cardLine.length() != 44) {
                        return ActionResult.getErrorResult("处理卡号[" + cardLine + "]时出现错误，请检查卡号是否正确，有效的卡号为44位，用'='结尾。");
                    }
                    try {
                        String decodeCardLine = SecurityUtils.aesDecrypt(cardLine);
                        String[] cardInfo = decodeCardLine.split("\\|");
                        int point = Integer.parseInt(cardInfo[0]);
                        account.changePoint(point, "卡号充值");
                        totalPoint += point;
                        log.info("账户[roomId=" + account.getRoomId() + "]卡号[" + cardLine + "]充值[" + decodeCardLine + "]");
                        IOUtils.write(cardLine + "\n", new FileOutputStream(usedcardFile, true), "utf-8");
                        settingConfig.saveSetting(liveManSetting);
                    } catch (Throwable e) {
                        log.error("账户充值发生错误[roomId=" + account.getRoomId() + ", cardLine=" + cardLine + "]", e);
                        return ActionResult.getErrorResult("处理卡号[" + cardLine + "]时出现错误，请检查卡号是否正确。");
                    }
                }
            }
            return ActionResult.getSuccessResult(totalPoint);
        } catch (Throwable e) {
            log.error("账户充值未知错误[roomId=" + account.getRoomId() + ", cards=" + cards + "]", e);
            return ActionResult.getErrorResult("操作失败，请联系管理员!");
        }
    }

    @RequestMapping("/apPointChange.json")
    public ActionResult apPointChange(String accountId, int point) {
        AccountInfo account = (AccountInfo) session.getAttribute("account");
        if (!account.isAdmin()) {
            return ActionResult.getErrorResult("权限不足");
        }
        log.info("apPointChange() operator=" + account.getAccountId() + ", accountId=" + accountId + ", point=" + point);
        AccountInfo byAccountId = liveManSetting.findByAccountId(accountId);
        long result = byAccountId.changePoint(point, "管理员操作");
        settingConfig.saveSetting(liveManSetting);
        return ActionResult.getSuccessResult("账户[" + byAccountId.getAccountId() + "]当前AP点数为:" + result);
    }

    @RequestMapping("/editAccount.json")
    public ActionResult editAccount(@RequestBody AccountInfoVO accountInfoVO) {
        AccountInfo account = (AccountInfo) session.getAttribute("account");
        AccountInfo byAccountId = liveManSetting.findByAccountId(account.getAccountId());
        if (byAccountId != null) {
            byAccountId.setDescription(accountInfoVO.getDescription());
            byAccountId.setPostBiliDynamic(accountInfoVO.isPostBiliDynamic());
            byAccountId.setAutoRoomTitle(accountInfoVO.isAutoRoomTitle());
            byAccountId.setSaveCookies(accountInfoVO.isSaveCookies());
            byAccountId.setRtmpHost(accountInfoVO.getRtmpHost());
            if (!StringUtils.containsOnly(accountInfoVO.getRtmpPasswordReal(), new char[]{'*'})) {
                byAccountId.setRtmpPassword(accountInfoVO.getRtmpPasswordReal());
            }
            if (byAccountId.isSaveCookies()) {
                byAccountId.setCookies(account.readCookies());
            } else {
                byAccountId.setCookies(null);
            }
        } else {
            return ActionResult.getErrorResult("尝试编辑的账户不存在，请刷新页面后重试");
        }
        try {
            settingConfig.saveSetting(liveManSetting);
        } catch (Exception e) {
            log.error("保存系统配置信息失败", e);
            return ActionResult.getErrorResult("系统内部错误，请联系管理员");
        }
        return ActionResult.getSuccessResult(null);
    }
}
