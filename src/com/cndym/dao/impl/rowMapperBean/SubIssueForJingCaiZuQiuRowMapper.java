package com.cndym.dao.impl.rowMapperBean;

import com.cndym.bean.tms.SubIssueForJingCaiZuQiu;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: mengjingyi
 * Date: 12-2-14
 * Time: 下午6:02
 */
public class SubIssueForJingCaiZuQiuRowMapper implements RowMapper<SubIssueForJingCaiZuQiu> {

    @Override
    public SubIssueForJingCaiZuQiu mapRow(ResultSet resultSet, int i) throws SQLException {
        SubIssueForJingCaiZuQiu subIssueForJingCaiZuQiu = new SubIssueForJingCaiZuQiu();
        subIssueForJingCaiZuQiu.setId(resultSet.getLong("id"));
        subIssueForJingCaiZuQiu.setLotteryCode(resultSet.getString("lottery_code"));
        subIssueForJingCaiZuQiu.setPlayCode(resultSet.getString("play_code"));
        subIssueForJingCaiZuQiu.setPollCode(resultSet.getString("poll_code"));
        subIssueForJingCaiZuQiu.setIssue(resultSet.getString("issue"));
        subIssueForJingCaiZuQiu.setSn(resultSet.getString("sn"));
        subIssueForJingCaiZuQiu.setWeek(resultSet.getString("week"));
        subIssueForJingCaiZuQiu.setMatchId(resultSet.getString("match_id"));
        subIssueForJingCaiZuQiu.setMatchColor(resultSet.getString("match_color"));
        subIssueForJingCaiZuQiu.setEndTime(resultSet.getTimestamp("end_time"));
        subIssueForJingCaiZuQiu.setMatchName(resultSet.getString("match_name"));
        subIssueForJingCaiZuQiu.setMainTeam(resultSet.getString("main_team"));
        subIssueForJingCaiZuQiu.setGuestTeam(resultSet.getString("guest_team"));
        subIssueForJingCaiZuQiu.setEndOperator(resultSet.getInt("end_operator"));
        subIssueForJingCaiZuQiu.setBonusOperator(resultSet.getInt("bonus_operator"));
        subIssueForJingCaiZuQiu.setCreateTime(resultSet.getTimestamp("create_time"));
        subIssueForJingCaiZuQiu.setUpdateTime(resultSet.getTimestamp("update_time"));

        subIssueForJingCaiZuQiu.setMainTeamHalfScore(resultSet.getInt("main_team_half_score"));
        subIssueForJingCaiZuQiu.setGuestTeamHalfScore(resultSet.getInt("guest_team_half_score"));
        subIssueForJingCaiZuQiu.setMainTeamScore(resultSet.getInt("main_team_score"));
        subIssueForJingCaiZuQiu.setGuestTeamScore(resultSet.getInt("guest_team_score"));
        
        subIssueForJingCaiZuQiu.setLetBall(resultSet.getString("let_ball"));

        subIssueForJingCaiZuQiu.setWinOrNegaSp(resultSet.getDouble("win_or_nega_sp"));
        subIssueForJingCaiZuQiu.setNegaSp(resultSet.getDouble("nega_sp"));
        subIssueForJingCaiZuQiu.setWinSp(resultSet.getDouble("win_sp"));
        subIssueForJingCaiZuQiu.setFlatSp(resultSet.getDouble("flat_sp"));

        subIssueForJingCaiZuQiu.setScoreSp(resultSet.getDouble("score_sp"));
        subIssueForJingCaiZuQiu.setScore00Sp(resultSet.getDouble("score_00_sp"));
        subIssueForJingCaiZuQiu.setScore01Sp(resultSet.getDouble("score_01_sp"));
        subIssueForJingCaiZuQiu.setScore02Sp(resultSet.getDouble("score_02_sp"));
        subIssueForJingCaiZuQiu.setScore03Sp(resultSet.getDouble("score_03_sp"));
        subIssueForJingCaiZuQiu.setScore04Sp(resultSet.getDouble("score_04_sp"));
        subIssueForJingCaiZuQiu.setScore05Sp(resultSet.getDouble("score_05_sp"));
        subIssueForJingCaiZuQiu.setScore10Sp(resultSet.getDouble("score_10_sp"));
        subIssueForJingCaiZuQiu.setScore11Sp(resultSet.getDouble("score_11_sp"));
        subIssueForJingCaiZuQiu.setScore12Sp(resultSet.getDouble("score_12_sp"));
        subIssueForJingCaiZuQiu.setScore13Sp(resultSet.getDouble("score_13_sp"));
        subIssueForJingCaiZuQiu.setScore14Sp(resultSet.getDouble("score_14_sp"));
        subIssueForJingCaiZuQiu.setScore15Sp(resultSet.getDouble("score_15_sp"));
        subIssueForJingCaiZuQiu.setScore20Sp(resultSet.getDouble("score_20_sp"));
        subIssueForJingCaiZuQiu.setScore21Sp(resultSet.getDouble("score_21_sp"));
        subIssueForJingCaiZuQiu.setScore22Sp(resultSet.getDouble("score_22_sp"));
        subIssueForJingCaiZuQiu.setScore23Sp(resultSet.getDouble("score_23_sp"));
        subIssueForJingCaiZuQiu.setScore24Sp(resultSet.getDouble("score_24_sp"));
        subIssueForJingCaiZuQiu.setScore25Sp(resultSet.getDouble("score_25_sp"));
        subIssueForJingCaiZuQiu.setScore30Sp(resultSet.getDouble("score_30_sp"));
        subIssueForJingCaiZuQiu.setScore31Sp(resultSet.getDouble("score_31_sp"));
        subIssueForJingCaiZuQiu.setScore32Sp(resultSet.getDouble("score_32_sp"));
        subIssueForJingCaiZuQiu.setScore33Sp(resultSet.getDouble("score_33_sp"));
        subIssueForJingCaiZuQiu.setScore40Sp(resultSet.getDouble("score_40_sp"));
        subIssueForJingCaiZuQiu.setScore41Sp(resultSet.getDouble("score_41_sp"));
        subIssueForJingCaiZuQiu.setScore42Sp(resultSet.getDouble("score_42_sp"));
        subIssueForJingCaiZuQiu.setScore50Sp(resultSet.getDouble("score_50_sp"));
        subIssueForJingCaiZuQiu.setScore51Sp(resultSet.getDouble("score_51_sp"));
        subIssueForJingCaiZuQiu.setScore52Sp(resultSet.getDouble("score_52_sp"));

        subIssueForJingCaiZuQiu.setTotalGoalSp(resultSet.getDouble("total_goal_sp"));
        subIssueForJingCaiZuQiu.setTotalGoal0Sp(resultSet.getDouble("total_goal_0_sp"));
        subIssueForJingCaiZuQiu.setTotalGoal1Sp(resultSet.getDouble("total_goal_1_sp"));
        subIssueForJingCaiZuQiu.setTotalGoal2Sp(resultSet.getDouble("total_goal_2_sp"));
        subIssueForJingCaiZuQiu.setTotalGoal3Sp(resultSet.getDouble("total_goal_3_sp"));
        subIssueForJingCaiZuQiu.setTotalGoal4Sp(resultSet.getDouble("total_goal_4_sp"));
        subIssueForJingCaiZuQiu.setTotalGoal5Sp(resultSet.getDouble("total_goal_5_sp"));
        subIssueForJingCaiZuQiu.setTotalGoal6Sp(resultSet.getDouble("total_goal_6_sp"));
        subIssueForJingCaiZuQiu.setTotalGoal7Sp(resultSet.getDouble("total_goal_7_sp"));

        subIssueForJingCaiZuQiu.setHalfCourtSp(resultSet.getDouble("half_court_sp"));
        subIssueForJingCaiZuQiu.setHalfCourtFFSp(resultSet.getDouble("half_court_ff_sp"));
        subIssueForJingCaiZuQiu.setHalfCourtFPSp(resultSet.getDouble("half_court_fp_sp"));
        subIssueForJingCaiZuQiu.setHalfCourtFSSp(resultSet.getDouble("half_court_fs_sp"));
        subIssueForJingCaiZuQiu.setHalfCourtPFSp(resultSet.getDouble("half_court_pf_sp"));
        subIssueForJingCaiZuQiu.setHalfCourtPPSp(resultSet.getDouble("half_court_pp_sp"));
        subIssueForJingCaiZuQiu.setHalfCourtPSSp(resultSet.getDouble("half_court_ps_sp"));
        subIssueForJingCaiZuQiu.setHalfCourtSFSp(resultSet.getDouble("half_court_sf_sp"));
        subIssueForJingCaiZuQiu.setHalfCourtSPSp(resultSet.getDouble("half_court_sp_sp"));
        subIssueForJingCaiZuQiu.setHalfCourtSSSp(resultSet.getDouble("half_court_ss_sp"));
        
        subIssueForJingCaiZuQiu.setSpfWinOrNegaSp(resultSet.getDouble("spf_win_or_nega_sp"));
        subIssueForJingCaiZuQiu.setSpfNegaSp(resultSet.getDouble("spf_nega_sp"));
        subIssueForJingCaiZuQiu.setSpfFlatSp(resultSet.getDouble("spf_flat_sp"));
        subIssueForJingCaiZuQiu.setSpfWinSp(resultSet.getDouble("spf_win_sp"));
        
        subIssueForJingCaiZuQiu.setOperatorsAward(resultSet.getInt("operators_award"));

        subIssueForJingCaiZuQiu.setEndDanShiTime(resultSet.getTimestamp("end_dan_shi_time"));
        subIssueForJingCaiZuQiu.setEndFuShiTime(resultSet.getTimestamp("end_fu_shi_time"));
        subIssueForJingCaiZuQiu.setEndStatus(resultSet.getInt("end_status"));

        subIssueForJingCaiZuQiu.setInputAwardStatus(resultSet.getInt("input_award_status"));

        return subIssueForJingCaiZuQiu;
    }
}
