package cn.linshiyou.financialFinalContest.goods.service;

import cn.linshiyou.financialFinalContest.goods.dao.dto.GoodsDTO;
import cn.linshiyou.financialFinalContest.goods.dao.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.Page;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
     * @return
     */
    void add(Goods good, HttpServletRequest request);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    GoodsDTO getByIdSelf(Serializable id);

    /**
     * 根据用户id和欲交换的物品价值生成最小物品集合
     * @param userId
     * @param price
     * @return
     */
    List<Goods> getLeastContainer(Long userId, Double price);
}
