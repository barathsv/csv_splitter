package code.barathsv.cvssplitter;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class MainApplication 
{
	public int splitFile(String filePath, String folderPath, int numLines, String prefix) throws Exception
	{
		Scanner input = null;
		int rowNum=0;
		int outputFileCount=0;
		try 
		{
			input = new Scanner(new File(filePath));
			final String header = input.nextLine().replaceAll("  *", " ");
			FileWriter fileWriter = null;
			
			while (input.hasNextLine()) 
			{
				if (rowNum % (numLines-1) == 0 ) 
				{
					if(rowNum!=0 && fileWriter!=null)
						fileWriter.close();
					
					++outputFileCount;
					rowNum=0;
					File outputFile = new File(folderPath + "/" + prefix + "_" + outputFileCount + ".csv");
					if (outputFile.createNewFile()) 
					{
						System.out.println("File created: " + outputFile.getName());
					} 
					else 
					{
						System.out.println("File already exists. Overriding!");
						outputFile.delete();
						outputFile.createNewFile();
					}
					
					fileWriter = new FileWriter(outputFile);
					fileWriter.write(header+'\n');
					System.out.println(header);
				}
				
				String rowText = input.nextLine().replaceAll("  *", " ");
				fileWriter.write(rowText);
				
				if((rowNum+1) % (numLines-1) != 0)
					fileWriter.write('\n');
				
				System.out.println("ROW " + ++rowNum + " : " + rowText);
			}
			
			System.out.println("TOTAL Files created = " + outputFileCount);
			
			return outputFileCount;
		}
		catch (Exception e) 
		{
			throw e;
		}
		finally 
		{
			if(input!=null)
				input.close();
		}

	}
	
	public static void main(String[] args) 
	{
		
	}
}