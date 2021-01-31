package com.bjlemon.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjlemon.commonutils.ResultCode;
import com.bjlemon.eduservice.entity.EduSubject;
import com.bjlemon.eduservice.entity.excel.SubjectData;
import com.bjlemon.eduservice.service.EduSubjectService;
import com.bjlemon.servicebase.exceptionhandler.LemonException;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    private EduSubjectService subjectService;

    public SubjectExcelListener(){}

    public SubjectExcelListener(EduSubjectService subjectService){
        this.subjectService=subjectService;
    }

    //逐行读取Excel内容
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData==null){
            throw new LemonException(ResultCode.ERROR,"文件数据为空");
        }
        EduSubject oneSubject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
        if(oneSubject==null){
            oneSubject=new EduSubject();
            oneSubject.setParentId("0");
            oneSubject.setTitle(subjectData.getOneSubjectName());

            subjectService.save(oneSubject);
        }
        String pid=oneSubject.getId();
        EduSubject twoSubject = this.existTwoSubject(subjectService,subjectData.getTwoSubjectName(),pid);
        if(twoSubject==null){
            twoSubject=new EduSubject();
            twoSubject.setParentId(pid);
            twoSubject.setTitle(subjectData.getTwoSubjectName());

            subjectService.save(twoSubject);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    private EduSubject existOneSubject(EduSubjectService subjectService,String subjectName){

        QueryWrapper<EduSubject> wrapper=new QueryWrapper<>();
        wrapper.eq("title",subjectName);
        wrapper.eq("parent_id","0");
        EduSubject one = subjectService.getOne(wrapper);
        return one;
    }

    private EduSubject existTwoSubject(EduSubjectService subjectService,String subjectName,String pid){

        QueryWrapper<EduSubject> wrapper=new QueryWrapper<>();
        wrapper.eq("title",subjectName);
        wrapper.eq("parent_id",pid);
        EduSubject two = subjectService.getOne(wrapper);
        return two;
    }
}
