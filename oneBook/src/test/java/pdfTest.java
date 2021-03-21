import com.spire.pdf.*;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class pdfTest {
    /**
     * 转换成word不能用
     */
    @Test
    public void testPdfToWord(){
        PdfDocument pdf = new PdfDocument("E:\\pdftest.pdf");
        pdf.saveToFile("E:\\ToWord.doc",FileFormat.DOCX);
    }

    @Test
    public void test(){
        String substring = "1token2".substring(1, "1token2".length() - 1);
    }
}
