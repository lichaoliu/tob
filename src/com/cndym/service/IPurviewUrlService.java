package com.cndym.service;

import java.util.List;
import java.util.Map;

import com.cndym.bean.sys.PurviewUrl;
import com.cndym.utils.hibernate.PageBean;

/**
 * User: MengJingyi
 * Date: 11-6-20 下午11:19
 */

public interface IPurviewUrlService extends IGenericeService<PurviewUrl> {
    public PurviewUrl getPurviewUrl(PurviewUrl purviewUrl);

    public PageBean getPurviewUrlList(PurviewUrl purviewUrl);

    public PageBean getPurviewUrlList(String codeIndex1);

    public List getPurviewUrlFatherList();

    public List<PurviewUrl> getPurviewUrlList();

    public List getPurviewUrlListByGroup(String groupCode);

    public List getPurviewUrlListByGroupNew(String groupCode);

    public Map<String, String> getPurviewUrlBycode(String code);
}
