package cn.linshiyou.financialFinalContest.search;

import cn.linshiyou.financialFinalContest.search.pojo.GoodsInfo;
import cn.linshiyou.financialFinalContest.search.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: LJ
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class Estest {

    @Autowired
    private SearchService esGoodsService;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * ElasticsearchRestTemplate基本用法
     * https://blog.csdn.net/orange_bug/article/details/117519866
     *
     * 查询搜索
     * https://blog.csdn.net/qq_45071180/article/details/122702830
     */

    /**
     * 创建索引和映射
     */
    @Test
    public void createIndex(){

        esGoodsService.createIndexMappingIfNot();

    }

    /**
     * 搜索全部数据，分页显示，按price字段排序
     */
    @Test
    public void select(){
        MatchAllQueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
        // 分页
        Pageable pageable = PageRequest.of(0, 5);

        FieldSortBuilder builder = new FieldSortBuilder("price").order(SortOrder.DESC);

//        执行查询
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withPageable(pageable)
                .withSort(builder)
                .build();

        SearchHits<GoodsInfo> searchHits = elasticsearchRestTemplate.search(query, GoodsInfo.class);
        //封装page对象
        List<GoodsInfo> goodsInfos = new ArrayList<>();
        for (SearchHit<GoodsInfo> hit : searchHits) {
            goodsInfos.add(hit.getContent());
        }
        Page<GoodsInfo> page = new PageImpl<>(goodsInfos,pageable,searchHits.getTotalHits());
        log.info(page.getContent()+"");
    }
    /**
     * 条件搜索
     */
    @Test
    public void selectByCondition(){
        // 搜索searchString字段中 包含 测 的文档
        TermQueryBuilder builder = QueryBuilders.termQuery("searchString", "测");


        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(builder)
                .build();

        SearchHits<GoodsInfo> searchHits = elasticsearchRestTemplate.search(query, GoodsInfo.class);

        List<GoodsInfo> goodsInfos = new ArrayList<>();
        for (SearchHit<GoodsInfo> hit : searchHits) {
            goodsInfos.add(hit.getContent());
        }

        log.info(goodsInfos.toString());
    }

    /**
     * 测试服务
     */
    @Test
    public void testService(){
        Page<GoodsInfo> goodsInfoPage = esGoodsService.selectByCondition(0, 3, "洗衣机",
                300D, false, null, 12L);
        log.info(goodsInfoPage.getContent()+"");
    }

}
