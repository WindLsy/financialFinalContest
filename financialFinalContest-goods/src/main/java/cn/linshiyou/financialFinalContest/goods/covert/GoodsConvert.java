package cn.linshiyou.financialFinalContest.goods.convert;

import cn.linshiyou.financialFinalContest.goods.dao.dto.GoodsDTO;
import cn.linshiyou.financialFinalContest.goods.dao.entity.Goods;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author: LJ
 * @Description: GoodsVo转换为GoodsVo
 */
@Mapper
public interface GoodsConvert {

    GoodsConvert INSTANCE = Mappers.getMapper(GoodsConvert.class);

    /**
     * goods -> goods
     * @param goods
     * @return
     */
    GoodsDTO goods2DTO(Goods goods);

    /**
     * list -> goodsDTO
     * @param goodsList
     * @return
     */
    List<GoodsDTO> goodsList2DTO(List<Goods> goodsList);

}
