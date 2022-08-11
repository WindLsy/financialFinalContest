package cn.linshiyou.financialFinalContest.search.service.Impl;

import cn.linshiyou.financialFinalContest.search.pojo.GoodsDTO;
import cn.linshiyou.financialFinalContest.search.pojo.GoodsInfo;
import cn.linshiyou.financialFinalContest.search.service.SearchService;
import cn.linshiyou.financialFinalContest.search.util.Constant;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: LJ
 * @Description:
 */
@Service
public class SearchServiceImp implements SearchService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;


    /**
     * 向es中添加数据/修改数据
     * @param goodsDTO
     */
    @Override
    public void addOrUpdateEsGoodsDTO(GoodsDTO goodsDTO) {
        elasticsearchRestTemplate.save(goodsDTO, IndexCoordinates.of(Constant.GOODS_INFO));
    }

    /**
     * 修改数据[弃用]
     */
    @Override
    public void UpdateEsGoodsDTO(GoodsDTO goodsDTO) {


        Document document = Document.create();
        if (goodsDTO.getTypeId()!=null){
            document.put("typeId", goodsDTO.getTypeId());
            document.put("typeName", goodsDTO.getTypeName());
        }
        if (goodsDTO.getStatusId()!=null){
            document.put("statusId", goodsDTO.getStatusId());
            document.put("statusName", goodsDTO.getStatusName());
        }
        if (!StringUtils.isEmpty(goodsDTO.getDescription())){
            document.put("description", goodsDTO.getDescription());
        }
        if (!StringUtils.isEmpty(goodsDTO.getImage())){
            document.put("image", goodsDTO.getImage());
        }
        if (goodsDTO.getPrice()!=null){
            document.put("price", goodsDTO.getPrice());
        }
        if (goodsDTO.getUserId()!=null){
            document.put("userId", goodsDTO.getUserId());
        }
        if (!StringUtils.isEmpty(goodsDTO.getName())){
            document.put("name", goodsDTO.getName());
        }

        UpdateQuery updateQuery = UpdateQuery.builder(String.valueOf(goodsDTO.getId()))
                .withDocument(document)
                .build();

        elasticsearchRestTemplate.update(updateQuery, IndexCoordinates.of(Constant.GOODS_INFO));
    }

    /**
     * 创建索引和映射，如果存在就不用创建
     */
    @Override
    public void createIndexMappingIfNot() {

        boolean exists = elasticsearchRestTemplate.indexOps(GoodsInfo.class).exists();
        if (!exists){
            elasticsearchRestTemplate.indexOps(GoodsInfo.class).create();
            Document document = elasticsearchRestTemplate.indexOps(GoodsInfo.class).createMapping();
            elasticsearchRestTemplate.indexOps(GoodsInfo.class).putMapping(document);
        }
    }

    /**
     * 根据条件搜索物品
     * @param startPage 起始页
     * @param sizePage 每页大小
     * @param searchString 物品的名称/类型名/描述
     * @param price 价格
     * @param isUp 这个价格以上/以下
     * @param userId 用户id
     * @param typeId 类型id
     * @param statusId 商品状态id
     * @return
     */
    @Override
    public Page<GoodsInfo> selectByCondition(int startPage, int sizePage, String searchString, Double price, boolean isUp, Long userId, Long typeId, Long statusId) {

        Pageable pageable = PageRequest.of(startPage, sizePage);
        FieldSortBuilder sortBuilder;

        if (isUp){
            sortBuilder = new FieldSortBuilder("price").order(SortOrder.DESC);
        }else {
            sortBuilder = new FieldSortBuilder("price").order(SortOrder.ASC);
        }

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        // 商品状态
        if (statusId!=null){
            queryBuilder.must(QueryBuilders.matchQuery("statusId",statusId));
        }

        if (!StringUtils.isEmpty(searchString)){
            queryBuilder.must(QueryBuilders.matchQuery("searchString", searchString));
        }
        if (price!=null){
            if (isUp){
                RangeQueryBuilder ltePrice = QueryBuilders.rangeQuery("price").lte(price);
                queryBuilder.filter(ltePrice);
            } else{
                RangeQueryBuilder gtePrice = QueryBuilders.rangeQuery("price").gte(price);
                queryBuilder.filter(gtePrice);
            }
        }
        if (userId!=null){
            queryBuilder.must(QueryBuilders.matchQuery("userId", userId));
        }
        if (typeId!=null){
            queryBuilder.must(QueryBuilders.matchQuery("typeId", typeId));
        }


        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withSort(sortBuilder)
                .withQuery(queryBuilder)
                .build();

        SearchHits<GoodsInfo> searchHits = elasticsearchRestTemplate.search(query, GoodsInfo.class);

        //封装page对象
        List<GoodsInfo> goodsInfos = new ArrayList<>();
        for (SearchHit<GoodsInfo> hit : searchHits) {
            goodsInfos.add(hit.getContent());
        }
        Page<GoodsInfo> goodsInfoPage = new PageImpl<>(goodsInfos,pageable,searchHits.getTotalHits());
        return goodsInfoPage;
    }

    /**
     * 根据id删除文档
     * @param id 文档id
     */
    @Override
    public void deleteDoc(Long id) {

        elasticsearchRestTemplate.delete(String.valueOf(id), GoodsInfo.class);
    }
}
