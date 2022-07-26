package cn.linshiyou.financialFinalContest.goods.service;

import cn.linshiyou.financialFinalContest.goods.dao.dto.GoodsDTO;
import cn.linshiyou.financialFinalContest.goods.dao.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.Page;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LJ
 * @since 2022-07-23
 */
public interface GoodsService extends IService<Goods> {


    /**
     * 添加新的物品
     * @param good 物品类
     * @param file 图片图片
     * @return
     */
    void add(Goods good, MultipartFile file);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    GoodsDTO getByIdSelf(Serializable id);
}
