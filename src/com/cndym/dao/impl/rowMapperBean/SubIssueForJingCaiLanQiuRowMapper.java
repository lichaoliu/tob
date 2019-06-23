package com.cndym.dao.impl.rowMapperBean;

import com.cndym.bean.tms.SubIssueForJingCaiLanQiu;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: mengjingyi
 * Date: 12-2-14
 * Time: 下午6:02
 */
public class SubIssueForJingCaiLanQiuRowMapper implements RowMapper<SubIssueForJingCaiLanQiu> {

    @Override
    public SubIssueForJingCaiLanQiu mapRow(ResultSet resultSet, int i) throws SQLException {
        SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiu = new SubIssueForJingCaiLanQiu();
        subIssueForJingCaiLanQiu.setId(resultSet.getLong("id"));
        subIssueForJingCaiLanQiu.setLotteryCode(resultSet.getString("lottery_code"));
        subIssueForJingCaiLanQiu.setPlayCode(resultSet.getString("play_code"));
        subIssueForJingCaiLanQiu.setPollCode(resultSet.getString("poll_code"));
        subIssueForJingCaiLanQiu.setIssue(resultSet.getString("issue"));
        subIssueForJingCaiLanQiu.setSn(resultSet.getString("sn"));
        subIssueForJingCaiLanQiu.setWeek(resultSet.getString("week"));
        subIssueForJingCaiLanQiu.setMatchId(resultSet.getString("match_id"));
        subIssueForJingCaiLanQiu.setMatchColor(resultSet.getString("match_color"));
        subIssueForJingCaiLanQiu.setEndTime(resultSet.getTimestamp("end_time"));
        subIssueForJingCaiLanQiu.setMatchName(resultSet.getString("match_name"));
        subIssueForJingCaiLanQiu.setMainTeam(resultSet.getString("main_team"));
        subIssueForJingCaiLanQiu.setGuestTeam(resultSet.getString("guest_team"));
        subIssueForJingCaiLanQiu.setEndOperator(resultSet.getInt("end_operator"));
        subIssueForJingCaiLanQiu.setBonusOperator(resultSet.getInt("bonus_operator"));
        subIssueForJingCaiLanQiu.setCreateTime(resultSet.getTimestamp("create_time"));
        subIssueForJingCaiLanQiu.setUpdateTime(resultSet.getTimestamp("update_time"));

        subIssueForJingCaiLanQiu.setMainTeamHalfScore(resultSet.getInt("main_team_half_score"));
        subIssueForJingCaiLanQiu.setGuestTeamHalfScore(resultSet.getInt("guest_team_half_score"));
        subIssueForJingCaiLanQiu.setMainTeamScore(resultSet.getInt("main_team_score"));
        subIssueForJingCaiLanQiu.setGuestTeamScore(resultSet.getInt("guest_team_score"));
        subIssueForJingCaiLanQiu.setLetBall(resultSet.getString("let_ball"));

        subIssueForJingCaiLanQiu.setNegaSp(resultSet.getDouble("nega_sp"));
        subIssueForJingCaiLanQiu.setWinSp(resultSet.getDouble("win_sp"));
        subIssueForJingCaiLanQiu.setWinOrNegaSp(resultSet.getDouble("win_or_nega_sp"));

        subIssueForJingCaiLanQiu.setLetWinOrNegaSp(resultSet.getDouble("let_win_or_nega_sp"));
        subIssueForJingCaiLanQiu.setLetNegaSp(resultSet.getDouble("let_nega_sp"));
        subIssueForJingCaiLanQiu.setLetWinSp(resultSet.getDouble("let_win_sp"));

        subIssueForJingCaiLanQiu.setWinNegaDiffSp(resultSet.getDouble("win_nega_diff_sp"));
        subIssueForJingCaiLanQiu.setWinNegaDiffM1T5Sp(resultSet.getDouble("win_nega_diff_m_1t5_sp"));
        subIssueForJingCaiLanQiu.setWinNegaDiffM6T10Sp(resultSet.getDouble("win_nega_diff_m_6t10_sp"));
        subIssueForJingCaiLanQiu.setWinNegaDiffM11T15Sp(resultSet.getDouble("win_nega_diff_m_11t15_sp"));
        subIssueForJingCaiLanQiu.setWinNegaDiffM16T20Sp(resultSet.getDouble("win_nega_diff_m_16t20_sp"));
        subIssueForJingCaiLanQiu.setWinNegaDiffM21T25Sp(resultSet.getDouble("win_nega_diff_m_21t25_sp"));
        subIssueForJingCaiLanQiu.setWinNegaDiffM26Sp(resultSet.getDouble("win_nega_diff_m_26_sp"));
        subIssueForJingCaiLanQiu.setWinNegaDiffG1T5Sp(resultSet.getDouble("win_nega_diff_g_1t5_sp"));
        subIssueForJingCaiLanQiu.setWinNegaDiffG6T10Sp(resultSet.getDouble("win_nega_diff_g_6t10_sp"));
        subIssueForJingCaiLanQiu.setWinNegaDiffG11T15Sp(resultSet.getDouble("win_nega_diff_g_11t15_sp"));
        subIssueForJingCaiLanQiu.setWinNegaDiffG16T20Sp(resultSet.getDouble("win_nega_diff_g_16t20_sp"));
        subIssueForJingCaiLanQiu.setWinNegaDiffG21T25Sp(resultSet.getDouble("win_nega_diff_g_21t25_sp"));
        subIssueForJingCaiLanQiu.setWinNegaDiffG26Sp(resultSet.getDouble("win_nega_diff_g_26_sp"));

        subIssueForJingCaiLanQiu.setBigOrLittleSp(resultSet.getDouble("big_or_little_sp"));
        subIssueForJingCaiLanQiu.setBigSp(resultSet.getDouble("big_sp"));
        subIssueForJingCaiLanQiu.setLittleSp(resultSet.getDouble("little_sp"));

        subIssueForJingCaiLanQiu.setPreCast(resultSet.getString("pre_cast"));

        subIssueForJingCaiLanQiu.setOperatorsAward(resultSet.getInt("operators_award"));

        subIssueForJingCaiLanQiu.setEndDanShiTime(resultSet.getTimestamp("end_dan_shi_time"));
        subIssueForJingCaiLanQiu.setEndFuShiTime(resultSet.getTimestamp("end_fu_shi_time"));
        subIssueForJingCaiLanQiu.setEndStatus(resultSet.getInt("end_status"));
        subIssueForJingCaiLanQiu.setInputAwardStatus(resultSet.getInt("input_award_status"));
        return subIssueForJingCaiLanQiu;

    }
}
