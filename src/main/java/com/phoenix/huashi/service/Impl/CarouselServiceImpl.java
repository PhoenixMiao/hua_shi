package com.phoenix.huashi.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phoenix.huashi.common.CommonErrorCode;
import com.phoenix.huashi.common.CommonException;
import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.common.PageParam;
import com.phoenix.huashi.controller.request.GetListRequest;
import com.phoenix.huashi.entity.Carousel;
import com.phoenix.huashi.mapper.CarouselMapper;
import com.phoenix.huashi.service.CarouselService;
import com.phoenix.huashi.util.AssertUtil;
import com.phoenix.huashi.util.TimeUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.UploadResult;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.Upload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.genid.GenId;

import java.util.List;

import static com.phoenix.huashi.common.CommonConstants.COS_BUCKET_NAME;

@Service
public class CarouselServiceImpl implements CarouselService {

    @Autowired
    private TransferManager transferManager;

    @Autowired
    private CarouselMapper carouselMapper;

    @Autowired
    private COSClient cosClient;

    @Override
    public String uploadFile(Long projectId, String fileName, Integer type, MultipartFile multipartFile){
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());

        UploadResult uploadResult = null;
        String res = null;

        try {

            String name = multipartFile.getOriginalFilename();
//            String name=new String(multipartFile.getOriginalFilename().getBytes("ISO-8859-1"), "UTF-8");
            AssertUtil.notNull(name,CommonErrorCode.FILENAME_CAN_NOT_BE_NULL);
            String first=name.substring(0,name.lastIndexOf("."));
            String extension = name.substring(name.lastIndexOf("."));
            if(!extension.equals(".bmp") && !extension.equals(".gif") && !extension.equals(".jpg") && !extension.equals(".png")){
                throw new CommonException(CommonErrorCode.WRONG_FILE_FORMAT);
            }
            String time=TimeUtil.getCurrentTimestamp();
            PutObjectRequest putObjectRequest = new PutObjectRequest(COS_BUCKET_NAME, time + "." + name, multipartFile.getInputStream(), objectMetadata);

            // ???????????????????????????????????????Upload
            // ?????????????????? waitForUploadResult ???????????????????????????????????????UploadResult, ??????????????????
            Upload upload = transferManager.upload(putObjectRequest);
            uploadResult = upload.waitForUploadResult();

            res =  cosClient.getObjectUrl(COS_BUCKET_NAME,time + "." +name).toString();
//            res = URLDecoder.decode(res, "utf-8");

            Carousel carousel=Carousel.builder().url(res).projectId(projectId).projectType(type).uploadTime(time).build();
            carouselMapper.insert(carousel);

        } catch (Exception e){
            //e.printStackTrace();
            throw new CommonException(CommonErrorCode.UPLOAD_FILE_FAIL);
        }


        // ??????????????????????????? transferManager ????????????????????????
        // ??????????????????????????????????????? -> ?????? TransferManager

        return res;
    }

    @Override
    public List<Carousel> getCarouselList(Integer number){
        return carouselMapper.getCarouselList(number);
    }

    @Override
    public Page<Carousel> getAllCarouselList(GetListRequest getListRequest){
        List<Carousel> carouselList = carouselMapper.selectAll();
        PageParam pageParam = getListRequest.getPageParam();
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize(), pageParam.getOrderBy());
        return new Page(new PageInfo<>(carouselList));
    }
}
