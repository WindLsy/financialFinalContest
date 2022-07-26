package cn.linshiyou.financialFinalContest.search.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "goodsinfo")
public class GoodsInfo implements Serializable {

    /**
     * 主键
     */
    @Id
    @Field(index = true, store = true, type = FieldType.Keyword)
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /**
     * 用户id
     */
    @Field(index = true, store = true, type = FieldType.Keyword)
    @JsonSerialize(using=ToStringSerializer.class)
    private Long userId;

    /**
     * 物品名称
     */
    @Field(index = true, store = true, type = FieldType.Text, analyzer = "ik_max_word", copyTo = "searchString")
    private String name;

    /**
     * 类型id
     */
    @Field(index = true, store = true, type = FieldType.Keyword)
    private Integer typeId;


    /**
     * 类型名称
     */
    @Field(index = false, store = true, type = FieldType.Keyword)
    private String typeName;


    /**
     * 物品价值
     */
    @Field(index = true, store = true, type = FieldType.Double)
    private Double price;

    /**
     * 物品图片
     */
    @Field(index = false, store = true, type = FieldType.Keyword)
    private String image;

    /**
     * 物品描述
     */
    @Field(index = true, store = true, type = FieldType.Text, analyzer = "ik_smart", copyTo = "searchString")
    private String description;

    /**
     * 物品状态id
     */
    @Field(index = true, store = true, type = FieldType.Keyword)
    private Integer statusId;

    /**
     * 物品状态
     */
    @Field(index = false, store = true, type = FieldType.Keyword)
    private String statusName;
    /**
     * 搜索索引
     */
    @Field(index = true, store = true, type = FieldType.Text, analyzer = "ik_max_word")
    private String searchString;
}
