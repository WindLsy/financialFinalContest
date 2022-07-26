package cn.linshiyou.financialFinalContest.search.service;

import cn.linshiyou.financialFinalContest.search.pojo.GoodsDTO;
import cn.linshiyou.financialFinalContest.search.pojo.GoodsInfo;
import org.springframework.data.domain.Page;

/**
 * @Author: LJ
 * @Description:
 */
public interface SearchService {


    /**
     * 添加数据或者修改
     */
    void addOrUpdateEsGoodsDTO(GoodsDTO goodsDTO);


    /**
     * 创建索引，如果存在就不用创建
     */
    void createIndexMappingIfNot();

    /**
     * 根据条件搜索物品
     * @param startPage 起始页
     * @param sizePage 每页大小
     * @param searchString 物品的名称/描述
     * @param price 价格
     * @param isUp 这个间隔以上/以下
     * @param typeId 类型id
     * @param statusId 类型id
     * @return
     */
    Page<GoodsInfo> selectByCondition(int startPage, int sizePage, String searchString, Double price, boolean isUp, Long typeId, Long statusId);

    /**
     * 删除文档
     * @param id 文档id
     */
    void deleteDoc(Long id);
}
