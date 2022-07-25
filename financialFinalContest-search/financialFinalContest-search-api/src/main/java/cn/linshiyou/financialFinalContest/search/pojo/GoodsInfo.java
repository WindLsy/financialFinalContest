package cn.linshiyou.financialFinalContest.search.pojo;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Author: LJ
 * @Description: 物品索引
 */
@Data
@Document(indexName = "goodsinfo")
public class GoodsInfo implements Serializable {

    /**
     * 主键
     */
    @Id
    @Field(index = true, store = true, type = FieldType.Keyword)
    private Long id;

    /**
     * 用户id
     */
    @Field(index = true, store = true, type = FieldType.Keyword)
    private Long userId;

    /**
     * 物品名称
     */
    @Field(index = true, store = true, type = FieldType.Text, searchAnalyzer = "ik_max_word", copyTo = "searchString")
    private String name;

    /**
     * 类型id
     */
    @Field(index = true, store = true, type = FieldType.Keyword)
    private Integer typeId;


    /**
     * 类型名称
     */
    @Field(index = true, store = true, type = FieldType.Text,  searchAnalyzer = "ik_smart", copyTo = "searchString")
    private String typeName;


    /**
     * 物品价值
     */
    @Field(index = true, store = true, type = FieldType.Double)
    private Float price;

    /**
     * 物品图片
     */
    @Field(index = true, store = true, type = FieldType.Keyword)
    private String image;

    /**
     * 物品描述
     */
    @Field(index = true, store = true, type = FieldType.Text, searchAnalyzer = "ik_smart", copyTo = "searchString")
    private String description;

    /**
     * 搜索索引
     */
    @Field(index = true, store = true, type = FieldType.Text, searchAnalyzer = "ik_max_word")
    private String searchString;
}
