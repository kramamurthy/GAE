package cs553.assgn3.filecreation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

public class CreateFiles 
{
	public static void main(String[] args) throws IOException
	{
		int i,onekbFiles=0,tenkbFiles=0,hundredkbFiles=0,onembFiles=0,tenmbFiles=0,hundredmbFile=0;
		int maxOnekbData=0,maxTenkbData=0,maxHundkbData=0,maxOnembData=0,maxTenmbData=0,maxHundmbData;
		for(i=0;i<325;i++)
		{
			SecureRandom random=new SecureRandom();
			String fileName = new BigInteger(51, random).toString(32);
			
			try
			{
				//   //home//kiran//Assignments//CloudComputing//ProgrammingAssignment3//DataSet//
				File newFile= new File(args[0]+""+fileName+".txt");
				newFile.createNewFile();
				FileWriter fWriter=new FileWriter(newFile);
				BufferedWriter bWriter=new BufferedWriter(fWriter);
				if(onekbFiles<100)
				{
					maxOnekbData=0;
					WriteToFile(bWriter, maxOnekbData,10);
					onekbFiles++;
				}
				else if(tenkbFiles<100)
				{
					maxTenkbData=0;
					WriteToFile(bWriter, maxTenkbData, 102);
					tenkbFiles++;
				}
				else if(hundredkbFiles<100)
				{
					maxHundkbData=0;
					WriteToFile(bWriter, maxHundkbData, 1024);
					hundredkbFiles++;
				}
				else if(onembFiles<20)
				{
					maxOnembData=0;
					WriteToFile(bWriter, maxOnembData, 10240);
					onembFiles++;
				}
				else if(tenmbFiles<5)
				{
					maxTenmbData=0;
					WriteToFile(bWriter, maxTenmbData, 102400);
					tenmbFiles++;
				}
				bWriter.close();
			}
			catch(IOException ex)
			{
				throw(ex);
			}
		}
	}
	
	public static String GenerateString()
	{
		int i;
		char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		StringBuilder dataStringBuilder=new StringBuilder();
		Random random=new Random();
		for(i=0;i<100;i++)
		{
			char c = chars[random.nextInt(chars.length)];
			dataStringBuilder.append(c);
		}
		String dataGenerated=dataStringBuilder.toString();
		return dataGenerated;
	}

	public static void WriteToFile(BufferedWriter writer,int maxData,int numberOfIterations) throws IOException
	{
		while(maxData<numberOfIterations)
		{
			String data = GenerateString();
			writer.write(data);
			writer.newLine();
			
			maxData++;
		}
		
	}
}


