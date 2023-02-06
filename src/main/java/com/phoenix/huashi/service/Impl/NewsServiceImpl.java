package com.phoenix.huashi.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phoenix.huashi.common.CommonErrorCode;
import com.phoenix.huashi.common.CommonException;
import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.common.PageParam;
import com.phoenix.huashi.controller.request.AddNewsRequest;
import com.phoenix.huashi.controller.request.GetBriefNewsListRequest;
import com.phoenix.huashi.controller.request.SearchRequest;
import com.phoenix.huashi.dto.news.BriefNews;
import com.phoenix.huashi.dto.notification.BriefNotification;
import com.phoenix.huashi.entity.News;
import com.phoenix.huashi.entity.Notification;
import com.phoenix.huashi.entity.User;
import com.phoenix.huashi.mapper.NewsMapper;
import com.phoenix.huashi.service.NewsService;
import com.phoenix.huashi.util.AssertUtil;
import com.phoenix.huashi.util.TimeUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.UploadResult;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.Upload;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

import static com.phoenix.huashi.common.CommonConstants.COS_BUCKET_NAME;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private TransferManager transferManager;

    @Autowired
    private COSClient cosClient;

    @Override
    public News getNewsById(Long id) {
        News news = newsMapper.getNewsById(id);
        if (news == null) throw new CommonException(CommonErrorCode.NEWS_NOT_EXIST);
        return news;
    }

    @Override
    public Page<BriefNews> getBriefNewsList(GetBriefNewsListRequest request) {
        if (request == null) return null;
        PageParam pageParam = request.getPageParam();
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize(), pageParam.getOrderBy());

        return new Page<>(new PageInfo<>(newsMapper.getBriefNewsList()));
    }

    @Override
    public Page<BriefNews> searchNews(SearchRequest searchRequest) {
        Example example = new Example(News.class);

        if (!StringUtils.isEmpty(searchRequest.getName())) {
            Example.Criteria nameCriteria = example.createCriteria();
            nameCriteria.orLike("title", "%" + searchRequest.getName() + "%");
            example.and(nameCriteria);
        }
        example.orderBy("id").desc();

        PageHelper.startPage(searchRequest.getPageParam().getPageNum(),
                searchRequest.getPageParam().getPageSize(),
                searchRequest.getPageParam().getOrderBy());
        List<News> newsList = newsMapper.selectByExample(example);
        Page page = new Page(new PageInfo(newsList));

        ArrayList<BriefNews> searchResponseArrayList = new ArrayList<>();
        for (News ele : newsList) {
            searchResponseArrayList.add(new BriefNews(ele.getId(),ele.getTitle(),ele.getPublishDate(),ele.getPicture()));
        }
        return new Page<>(searchRequest.getPageParam(), page.getTotal(), page.getPages(), searchResponseArrayList);
    }

    @Override
    public String uploadContentRTF(Long newsId, MultipartFile file) {
        News news = newsMapper.getNewsById(newsId);
        if (news == null) throw new CommonException(CommonErrorCode.NEWS_NOT_EXIST);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());

        UploadResult uploadResult = null;
        String res = null;

        try {

            String name = file.getOriginalFilename();
//            String name=new String(multipartFile.getOriginalFilename().getBytes("ISO-8859-1"), "UTF-8");
            AssertUtil.notNull(name, CommonErrorCode.FILENAME_CAN_NOT_BE_NULL);
            String first = name.substring(0, name.lastIndexOf("."));
            String extension = name.substring(name.lastIndexOf("."));
            String time = TimeUtil.getCurrentTimestamp();
            PutObjectRequest putObjectRequest = new PutObjectRequest(COS_BUCKET_NAME, "recruitProjectIntroductionRTF" + time + "." + name, file.getInputStream(), objectMetadata);

            // 高级接口会返回一个异步结果Upload
            // 可同步地调用 waitForUploadResult 方法等待上传完成，成功返回UploadResult, 失败抛出异常
            Upload upload = transferManager.upload(putObjectRequest);
            uploadResult = upload.waitForUploadResult();

            res = cosClient.getObjectUrl(COS_BUCKET_NAME, "NewsContentRTF" + time + "." + name).toString();
//            res = URLDecoder.decode(res, "utf-8");
            news.setContent(res);
            newsMapper.updateByPrimaryKey(news);

        } catch (Exception e) {
            //e.printStackTrace();
            throw new CommonException(CommonErrorCode.UPLOAD_FILE_FAIL);
        }
        return res;

    }


    @Override
    public Long addNews(AddNewsRequest request) {

        if (request == null) return null;

        News news = new News(null, request.getTitle(), TimeUtil.getCurrentTimestamp(), null, null);
        newsMapper.insert(news);
        return news.getId();

    }

    @Override
    public String uploadPicture(Long newsId, MultipartFile file) {
        News news = newsMapper.getNewsById(newsId);
        if (news == null) throw new CommonException(CommonErrorCode.NEWS_NOT_EXIST);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        UploadResult uploadResult = null;
        String res = null;
        String picture = news.getPicture();

        try{
            if(news.getPicture()!=null){
                cosClient.deleteObject(COS_BUCKET_NAME,picture.substring(picture.indexOf("picture"+news.getId())));
            }
        }catch (Exception e){
            throw new CommonException(CommonErrorCode.UPLOAD_FILE_FAIL);
        }

        try {

            String name = file.getOriginalFilename();
//            String name=new String(file.getOriginalFilename().getBytes("ISO-8859-1"), "UTF-8");
            AssertUtil.notNull(name, CommonErrorCode.FILENAME_CAN_NOT_BE_NULL);
            String first = name.substring(0, name.lastIndexOf("."));
            String extension = name.substring(name.lastIndexOf("."));

            if(!extension.equals(".bmp") && !extension.equals(".jpg") && !extension.equals(".png")){
                throw new CommonException(CommonErrorCode.WRONG_FILE_FORMAT);
            }
            PutObjectRequest putObjectRequest = new PutObjectRequest(COS_BUCKET_NAME, "picture"+news.getId() + "." + name, file.getInputStream(), objectMetadata);

            // 高级接口会返回一个异步结果Upload
            // 可同步地调用 waitForUploadResult 方法等待上传完成，成功返回UploadResult, 失败抛出异常
            Upload upload = transferManager.upload(putObjectRequest);
            uploadResult = upload.waitForUploadResult();

            res = cosClient.getObjectUrl(COS_BUCKET_NAME, "picture"+news.getId() + "." + name).toString();
            news.setPicture(res);
            newsMapper.updateByPrimaryKey(news);

        } catch (Exception e) {
            //e.printStackTrace();
            throw new CommonException(CommonErrorCode.UPLOAD_FILE_FAIL);
        }


        // 确定本进程不再使用 transferManager 实例之后，关闭之
        // 详细代码参见本页：高级接口 -> 关闭 TransferManager

        return res;
    }
}
