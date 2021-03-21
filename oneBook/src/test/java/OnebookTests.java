import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.File;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * @Auther: wdq
 * @Date: 2020/11/22 16:33
 * @Description:
 */
@SpringBootTest
public class OnebookTests {

    /**
     * 画pdf
     * @throws Exception
     */
    @Test
    public void testItextPdf() {
        try {
            // 创建字体格式-使用itext自带的字体，（字体名，编码，）
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            // 下面定义三种字体（字体，大小，样式）
            Font headfont = new Font(bfChinese, 14, Font.BOLD);
            Font  keyfont = new Font(bfChinese, 12, Font.BOLD);
            Font textfont = new Font(bfChinese, 10, Font.NORMAL);
            // 1.新建document对象
            Document document = new Document(PageSize.A4.rotate());// 建立一个Document对象
            // 2.建立一个书写器(Writer)与document对象关联
            File file = new File("D:\\PDFDemo.pdf");
            file.createNewFile();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            // 3.打开文档
            document.open();
            // 4.向文档中添加内容
            // 表格 ---每列的宽度
            PdfPTable table = new PdfPTable(new float[] { 80, 50, 80, 80, 90, 90, 95, 95 });
            // 设置表格上空白
            table.setSpacingBefore(100f);
            // 最大宽度
            table.setTotalWidth(660);
            table.setLockedWidth(true);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            // 第1行
            table.addCell(createCellHeight("【 2020 】年【 12 】月", headfont, Element.ALIGN_CENTER, 8,30));
            //第2,3行
            table.addCell(createCellRow("地市", keyfont, Element.ALIGN_CENTER,2));
            table.addCell(createCellRow("区县", keyfont, Element.ALIGN_CENTER,2));
            table.addCell(createCellRow("支撑放名称", keyfont, Element.ALIGN_CENTER,2));
            table.addCell(createCellRow("结算项目", keyfont, Element.ALIGN_CENTER,2));
            table.addCell(createCell("结算量", keyfont, Element.ALIGN_CENTER,2));
            table.addCell(createCellRow("结算系数（0~1）", keyfont, Element.ALIGN_CENTER,2));
            table.addCell(createCellRow("含税结算金额（元）", keyfont, Element.ALIGN_CENTER,2));
            table.addCell(createCell("含税单价（元/人/天）", keyfont, Element.ALIGN_CENTER));
            table.addCell(createCell("服务人天数", keyfont, Element.ALIGN_CENTER));
            // 第4行
            table.addCell(createCellHeight("郑州市", textfont, Element.ALIGN_CENTER,70));
            table.addCell(createCellHeight("", textfont, Element.ALIGN_CENTER,70));
            table.addCell(createCellHeight("", textfont, Element.ALIGN_CENTER,70));
            table.addCell(createCellHeight("", textfont, Element.ALIGN_CENTER,70));
            table.addCell(createCellHeight("", textfont, Element.ALIGN_CENTER,70));
            table.addCell(createCellHeight("", textfont, Element.ALIGN_CENTER,70));
            table.addCell(createCellHeight("1.00", textfont, Element.ALIGN_CENTER,70));
            table.addCell(createCellHeight("", textfont, Element.ALIGN_CENTER,70));
            // 第二个表格
            PdfPTable table2 = new PdfPTable(new float[] { 80, 50, 80, 80, 90, 220, 60 });
            BaseColor baseColor = new BaseColor(253, 233, 217);
            // 设置表格上空白
            table2.setSpacingBefore(20f);
            // 最大宽度
            table2.setTotalWidth(660);
            table2.setLockedWidth(true);
            table2.setHorizontalAlignment(Element.ALIGN_CENTER);
            // 第1行
            table2.addCell(createCellHeight("服务考核指标", textfont, Element.ALIGN_CENTER, 5,20));
            table2.addCell(createCellHeight("考核规则", textfont, Element.ALIGN_CENTER, 2,20));
            //第2行
            table2.addCell(createCellHeight("指标名称", textfont, Element.ALIGN_CENTER,20));
            table2.addCell(createCellHeight("目标值", textfont, Element.ALIGN_CENTER,20));
            table2.addCell(createCellHeight("完成值", textfont, Element.ALIGN_CENTER,20));
            table2.addCell(createCellHeight("完成占比", textfont, Element.ALIGN_CENTER,20));
            table2.addCell(createCellHeight("得分", textfont, Element.ALIGN_CENTER,20));
            table2.addCell(createCellHeightAndColor("得分规则", textfont, Element.ALIGN_CENTER,20,baseColor));
            table2.addCell(createCellHeightAndColor("分值", textfont, Element.ALIGN_CENTER,20,baseColor));
            // 第3行
            table2.addCell(createCellHeight("人均ARPU", textfont, Element.ALIGN_CENTER,30));
            table2.addCell(createCellHeight("", textfont, Element.ALIGN_CENTER,30));
            table2.addCell(createCellHeight("", textfont, Element.ALIGN_CENTER,30));
            table2.addCell(createCellHeight("", textfont, Element.ALIGN_CENTER,30));
            table2.addCell(createCellHeight("", textfont, Element.ALIGN_CENTER,30));
            table2.addCell(createCellHeightAndColor("在0分和满分之间线性得分 ", textfont, Element.ALIGN_UNDEFINED,30,baseColor));
            table2.addCell(createCellHeightAndColor("", textfont, Element.ALIGN_CENTER,30,baseColor));
            // 第4行
            table2.addCell(createCellHeight("人均DOU", textfont, Element.ALIGN_CENTER,30));
            table2.addCell(createCellHeight("", textfont, Element.ALIGN_CENTER,30));
            table2.addCell(createCellHeight("", textfont, Element.ALIGN_CENTER,30));
            table2.addCell(createCellHeight("", textfont, Element.ALIGN_CENTER,30));
            table2.addCell(createCellHeight("", textfont, Element.ALIGN_CENTER,30));
            table2.addCell(createCellHeightAndColor("在0分和满分之间线性得分 ", textfont, Element.ALIGN_UNDEFINED,30,baseColor));
            table2.addCell(createCellHeightAndColor("", textfont, Element.ALIGN_CENTER,30,baseColor));
            // 第5行
            table2.addCell(createCellHeight("接触客户数", textfont, Element.ALIGN_CENTER,30));
            table2.addCell(createCellHeight("", textfont, Element.ALIGN_CENTER,30));
            table2.addCell(createCellHeight("", textfont, Element.ALIGN_CENTER,30));
            table2.addCell(createCellHeight("", textfont, Element.ALIGN_CENTER,30));
            table2.addCell(createCellHeight("", textfont, Element.ALIGN_CENTER,30));
            table2.addCell(createCellHeightAndColor("在0分和满分之间线性得分 ", textfont, Element.ALIGN_UNDEFINED,30,baseColor));
            table2.addCell(createCellHeightAndColor("", textfont, Element.ALIGN_CENTER,30,baseColor));
            // 第6行
            table2.addCell(createCellHeight("个人新增数", textfont, Element.ALIGN_CENTER,30));
            table2.addCell(createCellHeight("", textfont, Element.ALIGN_CENTER,30));
            table2.addCell(createCellHeight("", textfont, Element.ALIGN_CENTER,30));
            table2.addCell(createCellHeight("", textfont, Element.ALIGN_CENTER,30));
            table2.addCell(createCellHeight("", textfont, Element.ALIGN_CENTER,30));
            table2.addCell(createCellHeightAndColor("在0分和满分之间线性得分 ", textfont, Element.ALIGN_UNDEFINED,30,baseColor));
            table2.addCell(createCellHeightAndColor("", textfont, Element.ALIGN_CENTER,30,baseColor));
            // 第7行
            table2.addCell(createCellHeight("宽带新增数", textfont, Element.ALIGN_CENTER,30));
            table2.addCell(createCellHeight("", textfont, Element.ALIGN_CENTER,30));
            table2.addCell(createCellHeight("", textfont, Element.ALIGN_CENTER,30));
            table2.addCell(createCellHeight("", textfont, Element.ALIGN_CENTER,30));
            table2.addCell(createCellHeight("", textfont, Element.ALIGN_CENTER,30));
            table2.addCell(createCellHeightAndColor("在0分和满分之间线性得分 ", textfont, Element.ALIGN_UNDEFINED,30,baseColor));
            table2.addCell(createCellHeightAndColor("", textfont, Element.ALIGN_CENTER,30,baseColor));
            // 第8行
            table2.addCell(createCellHeight("服务客户次数", textfont, Element.ALIGN_CENTER,30));
            table2.addCell(createCellHeight("", textfont, Element.ALIGN_CENTER,30));
            table2.addCell(createCellHeight("", textfont, Element.ALIGN_CENTER,30));
            table2.addCell(createCellHeight("", textfont, Element.ALIGN_CENTER,30));
            table2.addCell(createCellHeight("", textfont, Element.ALIGN_CENTER,30));
            table2.addCell(createCellHeightAndColor("在0分和满分之间线性得分 ", textfont, Element.ALIGN_UNDEFINED,30,baseColor));
            table2.addCell(createCellHeightAndColor("", textfont, Element.ALIGN_CENTER,30,baseColor));
            // 第9行
            table2.addCell(createCellHeight("总分", textfont, Element.ALIGN_CENTER,6,20));
            table2.addCell(createCellHeight("", textfont, Element.ALIGN_CENTER,20));
            document.add(table);
            document.add(table2);

            // 绝对定位
            PdfContentByte cb = writer.getDirectContent();
            BaseFont bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            cb.beginText();
            cb.setFontAndSize(bf, 10);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "公司名称（盖章）：", 175, 135, 0);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "公司名称（盖章）：【    公司】", 475, 135, 0);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "复核人（签字）：", 175, 110, 0);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "复核人（签字）：", 475, 110, 0);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "经办人（签字）： ", 175, 85, 0);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "经办人（签字）： ", 475, 85, 0);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "日期： ", 175, 60, 0);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "日期： ", 475, 60, 0);
            cb.endText();

            // 5.关闭文档
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * 创建单元格（指定字体、水平..）
     * @param value
     * @param font
     * @param align
     * @return
     */
    public PdfPCell createCell(String value, Font font, int align) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }

    /**
     *  设置行高height
     * @param value
     * @param font
     * @param align
     * @param height
     * @return
     */
    public PdfPCell createCellHeight(String value, Font font, int align,int height) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setMinimumHeight(height);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }

    /**
     *  设置颜色
     * @param value
     * @param font
     * @param align
     * @param height
     * @param color
     * @return
     */
    public PdfPCell createCellHeightAndColor(String value, Font font, int align,int height,BaseColor color) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setMinimumHeight(height);
        cell.setPhrase(new Phrase(value, font));
        cell.setBackgroundColor(color);
        return cell;
    }
    /**
     * 创建单元格（指定字体、水平居..、单元格跨x列合并）
     * @param value
     * @param font
     * @param align
     * @param colspan
     * @return
     */
    public PdfPCell createCell(String value, Font font, int align, int colspan) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }

    /**
     *
     * @param value
     * @param font
     * @param align
     * @param colspan
     * @param height
     * @return
     */
    public PdfPCell createCellHeight(String value, Font font, int align, int colspan,int height) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setMinimumHeight(height);
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }
    /**
     * 创建单元格（指定字体、水平居..、单元格跨x行合并）
     * @param value
     * @param font
     * @param align
     * @param rowspan
     * @return
     */
    public PdfPCell createCellRow(String value, Font font, int align, int rowspan) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setRowspan(rowspan);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }

    /**
     * 测试读取excel并操作excel
     * 测试excel -> zip
     * @throws IOException
     */
    @Test
    public void testZip() throws IOException {
        // copy文件到zip输出流中
        byte[] buf = new byte[1024];
        int cnt = 1,len;
        FileOutputStream out = new FileOutputStream("D:\\test_zip.zip");
        ZipOutputStream zos = new ZipOutputStream(out);
        // 1、读取模板--模板图在下面，可以自己画一个
        FileInputStream fileIn = new FileInputStream("D:\\test.xls");
        // 获取excel对象
        HSSFWorkbook wb = new HSSFWorkbook(fileIn);
        // 读取第一个sheet页
        HSSFSheet sheet = wb.getSheetAt(0);
        // 模拟循环10次，压缩10个excel到压缩包里面
        for (int i = 0; i < 10; i++) {
            // 2、填写数据
            // 获取第1行，第1列的单元格，然后填写数据
            // 一个excel有多个sheet
            // 一个sheet有多个row
            // 一个row有多个cell
            //  HSSFRow row = sheet.getRow(0);
            //  HSSFCell cell = row.getCell(0);
            //  cell.setCellValue("【2020】年【"+(cnt)+"】月");
            // 下面这一行与上面三行等价
            sheet.getRow(0).getCell(0).setCellValue( "【2020】年【"+(cnt)+"】月" );
            // 3、保存到输出流
            ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
            wb.write(fileOut);
            // 4、输出流转换成输入流
            byte[] content = fileOut.toByteArray();
            ByteArrayInputStream is = new ByteArrayInputStream(content);
            BufferedInputStream in = new BufferedInputStream(is);
            // 5、放到压缩流--填写待文件的名称
            zos.putNextEntry(new ZipEntry("test-wdq"+(cnt++)+".xls"));
            while ((len = in.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
            // 关闭流的顺序不能变，遵从"先开后关"的原则
            // 关闭流的顺序不对，会导致压缩包的文件损坏
            zos.flush();
            fileOut.close();
            zos.closeEntry();
        }
        // 关闭流的顺序不能变，遵从"先开后关"的原则
        wb.close();
        zos.close();
        out.close();
    }

    /**
     * 测试输出流
     * @throws IOException
     */
    @Test
    public void testStream() throws IOException {
        // copy文件到zip输出流中
        int len;
        byte[] buf = new byte[1024];
        int cnt = 1;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(out);
        // 1、读取模板
        FileInputStream fileIn = new FileInputStream("D:\\test.xls");
        HSSFWorkbook wb = new HSSFWorkbook(fileIn);
        HSSFSheet sheet = wb.getSheetAt(0);
        //循环
        for (int i = 0; i < 10; i++) {
            // 2、填写数据
            sheet.createRow(0).createCell(0).setCellValue( "【2020】年【"+(cnt)+"】月服务支撑结算单" );
            // 3、保存到输出流
            ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
            wb.write(fileOut);
            // 4、输出流转换成输入流
            byte[] content = fileOut.toByteArray();
            ByteArrayInputStream is = new ByteArrayInputStream(content);
            BufferedInputStream in = new BufferedInputStream(is);
            // 5、放到压缩流--填写待文件的名称
            zos.putNextEntry(new ZipEntry("test-wdq"+(cnt++)+".xls"));
            while ((len = in.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
            // 关闭各种流
            zos.flush();
            fileOut.close();
            zos.closeEntry();
        }
        wb.close();
        zos.close();
        out.close();
        // 将压缩输出流转换成输入流
        byte[] content = out.toByteArray();
        ByteArrayInputStream is = new ByteArrayInputStream(content);
        BufferedInputStream in = new BufferedInputStream(is);
    }
    /**
     * 测试阿里oss上传
     * @throws IOException
     */
    @Test
    public void testUploaded() throws IOException {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "oss-cn-beijing.aliyuncs.com";
        String accessKeyId = "LTAI4Fyn7XUM54taNPEggjkx";
        String accessKeySecret = "AFJufOvaKeCie9PmthohuUBcu0xom4";
        String bucketName = "onebook-wdq";
        String objectName = "user/123.txt";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 上传文件到指定的存储空间（bucketName）并将其保存为指定的文件名称（objectName）。
        String content = "Hello OSS";
        ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(content.getBytes()));
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     * 测试阿里oss删除
     * @throws IOException
     */
    @Test
    public void testDelete() throws IOException {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "oss-cn-beijing.aliyuncs.com";
        String accessKeyId = "LTAI4Fyn7XUM54taNPEggjkx";
        String accessKeySecret = "AFJufOvaKeCie9PmthohuUBcu0xom4";
        String bucketName = "onebook-wdq";
        // <yourObjectName>表示删除OSS文件时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        String objectName = "user/123.txt";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 删除文件。
        ossClient.deleteObject(bucketName, objectName);
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    @Test
    public void testDate(){
        Date t = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = df.format(t);
    }
}
