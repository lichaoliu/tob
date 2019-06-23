package com.cndym.dao.impl.rowMapperBean;

import com.cndym.bean.tms.SubIssueForBeiDan;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: mengjingyi
 * Date: 12-2-14
 * Time: 下午6:02
 */
public class SubIssueForBeiDanRowMapper implements RowMapper<SubIssueForBeiDan> {

    @Override
    public SubIssueForBeiDan mapRow(ResultSet resultSet, int i) throws SQLException {
        SubIssueForBeiDan subIssueForBeiDan = new SubIssueForBeiDan();
        subIssueForBeiDan.setId(resultSet.getLong("id"));
        subIssueForBeiDan.setLotteryCode(resultSet.getString("lottery_code"));
        subIssueForBeiDan.setIssue(resultSet.getString("issue"));
        subIssueForBeiDan.setSn(resultSet.getString("sn"));
        subIssueForBeiDan.setWeek(resultSet.getString("week"));
        subIssueForBeiDan.setMatchColor(resultSet.getString("match_color"));
        subIssueForBeiDan.setEndTime(resultSet.getTimestamp("end_time"));
        subIssueForBeiDan.setMatchName(resultSet.getString("match_name"));
        subIssueForBeiDan.setMainTeam(resultSet.getString("main_team"));
        subIssueForBeiDan.setGuestTeam(resultSet.getString("guest_team"));
        subIssueForBeiDan.setEndOperator(resultSet.getInt("end_operator"));
        subIssueForBeiDan.setBonusOperator(resultSet.getInt("bonus_operator"));
        subIssueForBeiDan.setCreateTime(resultSet.getTimestamp("create_time"));
        subIssueForBeiDan.setUpdateTime(resultSet.getTimestamp("update_time"));


        subIssueForBeiDan.setMainTeamHalfScore(resultSet.getInt("main_team_half_score"));
        subIssueForBeiDan.setGuestTeamHalfScore(resultSet.getInt("guest_team_half_score"));
        subIssueForBeiDan.setMainTeamScore(resultSet.getInt("main_team_score"));
        subIssueForBeiDan.setGuestTeamScore(resultSet.getInt("guest_team_score"));

        subIssueForBeiDan.setLetBall(resultSet.getString("let_ball"));

        subIssueForBeiDan.setWinOrNegaSp(resultSet.getDouble("win_or_nega_sp"));
        subIssueForBeiDan.setNegaSp(resultSet.getDouble("nega_sp"));
        subIssueForBeiDan.setWinSp(resultSet.getDouble("win_sp"));
        subIssueForBeiDan.setFlatSp(resultSet.getDouble("flat_sp"));

        subIssueForBeiDan.setScoreSp(resultSet.getDouble("score_sp"));
        subIssueForBeiDan.setScore00Sp(resultSet.getDouble("score_00_sp"));
        subIssueForBeiDan.setScore01Sp(resultSet.getDouble("score_01_sp"));
        subIssueForBeiDan.setScore02Sp(resultSet.getDouble("score_02_sp"));
        subIssueForBeiDan.setScore03Sp(resultSet.getDouble("score_03_sp"));
        subIssueForBeiDan.setScore04Sp(resultSet.getDouble("score_04_sp"));
        subIssueForBeiDan.setScore10Sp(resultSet.getDouble("score_10_sp"));
        subIssueForBeiDan.setScore11Sp(resultSet.getDouble("score_11_sp"));
        subIssueForBeiDan.setScore12Sp(resultSet.getDouble("score_12_sp"));
        subIssueForBeiDan.setScore13Sp(resultSet.getDouble("score_13_sp"));
        subIssueForBeiDan.setScore14Sp(resultSet.getDouble("score_14_sp"));
        subIssueForBeiDan.setScore20Sp(resultSet.getDouble("score_20_sp"));
        subIssueForBeiDan.setScore21Sp(resultSet.getDouble("score_21_sp"));
        subIssueForBeiDan.setScore22Sp(resultSet.getDouble("score_22_sp"));
        subIssueForBeiDan.setScore23Sp(resultSet.getDouble("score_23_sp"));
        subIssueForBeiDan.setScore24Sp(resultSet.getDouble("score_24_sp"));
        subIssueForBeiDan.setScore30Sp(resultSet.getDouble("score_30_sp"));
        subIssueForBeiDan.setScore31Sp(resultSet.getDouble("score_31_sp"));
        subIssueForBeiDan.setScore32Sp(resultSet.getDouble("score_32_sp"));
        subIssueForBeiDan.setScore33Sp(resultSet.getDouble("score_33_sp"));
        subIssueForBeiDan.setScore40Sp(resultSet.getDouble("score_40_sp"));
        subIssueForBeiDan.setScore41Sp(resultSet.getDouble("score_41_sp"));
        subIssueForBeiDan.setScore42Sp(resultSet.getDouble("score_42_sp"));

        subIssueForBeiDan.setTotalGoalSp(resultSet.getDouble("total_goal_sp"));
        subIssueForBeiDan.setTotalGoal0Sp(resultSet.getDouble("total_goal_0_sp"));
        subIssueForBeiDan.setTotalGoal1Sp(resultSet.getDouble("total_goal_1_sp"));
        subIssueForBeiDan.setTotalGoal2Sp(resultSet.getDouble("total_goal_2_sp"));
        subIssueForBeiDan.setTotalGoal3Sp(resultSet.getDouble("total_goal_3_sp"));
        subIssueForBeiDan.setTotalGoal4Sp(resultSet.getDouble("total_goal_4_sp"));
        subIssueForBeiDan.setTotalGoal5Sp(resultSet.getDouble("total_goal_5_sp"));
        subIssueForBeiDan.setTotalGoal6Sp(resultSet.getDouble("total_goal_6_sp"));
        subIssueForBeiDan.setTotalGoal7Sp(resultSet.getDouble("total_goal_7_sp"));

        subIssueForBeiDan.setShangXiaPanSp(resultSet.getDouble("shang_xia_pan_sp"));
        subIssueForBeiDan.setShangDanSp(resultSet.getDouble("shang_dan_sp"));
        subIssueForBeiDan.setShangShuangSp(resultSet.getDouble("shang_shuang_sp"));
        subIssueForBeiDan.setXiaDanSp(resultSet.getDouble("xia_dan_sp"));
        subIssueForBeiDan.setXiaShuangSp(resultSet.getDouble("xia_shuang_sp"));

        subIssueForBeiDan.setHalfCourtSp(resultSet.getDouble("half_court_sp"));
        subIssueForBeiDan.setHalfCourtFFSp(resultSet.getDouble("half_court_ff_sp"));
        subIssueForBeiDan.setHalfCourtFPSp(resultSet.getDouble("half_court_fp_sp"));
        subIssueForBeiDan.setHalfCourtFSSp(resultSet.getDouble("half_court_fs_sp"));
        subIssueForBeiDan.setHalfCourtPFSp(resultSet.getDouble("half_court_pf_sp"));
        subIssueForBeiDan.setHalfCourtPPSp(resultSet.getDouble("half_court_pp_sp"));
        subIssueForBeiDan.setHalfCourtPSSp(resultSet.getDouble("half_court_ps_sp"));
        subIssueForBeiDan.setHalfCourtSFSp(resultSet.getDouble("half_court_sf_sp"));
        subIssueForBeiDan.setHalfCourtSPSp(resultSet.getDouble("half_court_sp_sp"));
        subIssueForBeiDan.setHalfCourtSSSp(resultSet.getDouble("half_court_ss_sp"));

        subIssueForBeiDan.setOperatorsAward(resultSet.getInt("operators_award"));

        subIssueForBeiDan.setEndDanShiTime(resultSet.getTimestamp("end_dan_shi_time"));
        subIssueForBeiDan.setEndFuShiTime(resultSet.getTimestamp("end_fu_shi_time"));
        subIssueForBeiDan.setEndStatus(resultSet.getInt("end_status"));

        subIssueForBeiDan.setInputAwardStatus(resultSet.getInt("input_award_status"));
        subIssueForBeiDan.setDelay(resultSet.getInt("delay"));
        subIssueForBeiDan.setBackup1(resultSet.getString("Backup1"));
        subIssueForBeiDan.setBackup2(resultSet.getString("Backup2"));
        subIssueForBeiDan.setBackup3(resultSet.getString("Backup3"));


        return subIssueForBeiDan;
    }
}
