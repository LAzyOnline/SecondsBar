package com.fx.secondbar.bean;

/**
 * function:教程实体信息
 * author: frj
 * modify date: 2018/9/23
 */
public class TurialBean
{
    private String file;//null,
    private String status;//状态值
    private String content;//内容
    private String title;//标题
    private String sorts;//排序
    private String type;//类型
    private String img;//教程展示图片
    private String createtime;//创建时间
    private String course_ID;//教程id

    public String getFile()
    {
        return file;
    }

    public void setFile(String file)
    {
        this.file = file;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getSorts()
    {
        return sorts;
    }

    public void setSorts(String sorts)
    {
        this.sorts = sorts;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getImg()
    {
        return img;
    }

    public void setImg(String img)
    {
        this.img = img;
    }

    public String getCreatetime()
    {
        return createtime;
    }

    public void setCreatetime(String createtime)
    {
        this.createtime = createtime;
    }

    public String getCourse_ID()
    {
        return course_ID;
    }

    public void setCourse_ID(String course_ID)
    {
        this.course_ID = course_ID;
    }
}
