package demo;


import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;


public class ReadFroExcel
{
    static DataFormatter df = new DataFormatter();

    
 
    public static String[][] getData() throws IOException
    {
        
        FileInputStream fs= new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\java\\resources\\qa_codeathon_week3.xlsx");
        //FileInputStream fs= new FileInputStream("C:\\Users\\Dheeraj Kapoor\\Desktop\\Book1.xlsx");
        XSSFWorkbook workbook= new XSSFWorkbook(fs);
        XSSFSheet sheet= workbook.getSheetAt(0);
        int rowCount=sheet.getLastRowNum();  //6

        String[][] data = new String[rowCount][1];

        for(int i=1;i<=rowCount;i++)
        {
            XSSFRow row=sheet.getRow(i);
                for (Cell cell : row) {
                    // XSSFCell cell=row.getCell(i);   
                    data[i-1][0]=df.formatCellValue(cell);
        }
    }

        workbook.close();
        fs.close();

        return data;
    }     
    
}
