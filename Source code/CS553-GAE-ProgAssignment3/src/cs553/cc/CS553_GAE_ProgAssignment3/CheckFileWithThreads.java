package cs553.cc.CS553_GAE_ProgAssignment3;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.ThreadManager;
import com.google.appengine.api.blobstore.BlobInfo;

public class CheckFileWithThreads extends HttpServlet
{
	public void doPost(HttpServletRequest req,final HttpServletResponse resp) throws IOException
	{
		String fileName=req.getParameter("FileName");
		resp.getWriter().println(fileName);
		List<BlobInfo> readBlob=new LinkedList<BlobInfo>();
		int noOfFiles=readBlob.size();
		
		int upperLimit=noOfFiles/4;
		
		long startTime = System.currentTimeMillis();
		
		FindWithThreads thread1=new FindWithThreads(0,upperLimit,fileName,resp,startTime);
		Thread t1=ThreadManager.currentRequestThreadFactory().newThread(thread1);
		t1.run();
		
		FindWithThreads thread2=new FindWithThreads(upperLimit,(2*upperLimit),fileName,resp,startTime);
		Thread t2=ThreadManager.currentRequestThreadFactory().newThread(thread2);
		t2.run();
		
		FindWithThreads thread3=new FindWithThreads((2*upperLimit),(3*upperLimit),fileName,resp,startTime);
		Thread t3=ThreadManager.currentRequestThreadFactory().newThread(thread3);
		t3.run();
		
		FindWithThreads thread4=new FindWithThreads((3*upperLimit),(4*upperLimit),fileName,resp,startTime);
		Thread t4=ThreadManager.currentRequestThreadFactory().newThread(thread4);
		t4.run();
	}
}

class FindWithThreads implements Runnable
{
	public int lLimit,uLimit;
	public String fName;
	HttpServletResponse resp;
	List<BlobInfo> readBlob=new LinkedList<BlobInfo>();
	long sTime;
	public FindWithThreads(int lowLimit,int upperLimit,String fileName,HttpServletResponse response, long startTime)
	{
		lLimit=lowLimit;
		uLimit=upperLimit;
		fName=fileName;
		resp=response;
		sTime=startTime;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String fileNameInBlob;
		
		
		for(int i=lLimit;i<uLimit;i++)
        {
        	fileNameInBlob = readBlob.get(i).getFilename();
        	System.out.println(fileNameInBlob+","+fName+","+lLimit+","+uLimit);
        	try
        	{
        	resp.getWriter().println(fileNameInBlob);
        	resp.getWriter().println(fName);
        	resp.getWriter().println(lLimit);
        	resp.getWriter().println(uLimit);
        	}
        	catch(IOException ex)
    		{
    			ex.printStackTrace();
    		}
        	if(fileNameInBlob.equals(fName))
        	{
        		try
        		{
        		long eTime=System.currentTimeMillis()-sTime;
        		resp.setContentType("text/plain");
        		resp.getWriter().println("Total time taken to find the file is "+eTime+" ms");
        		break;
        		}
        		catch(IOException ex)
        		{
        			ex.printStackTrace();
        		}
        	}
        	       	
        }
		try
		{
			resp.setContentType("text/plain");
			resp.getWriter().println("File not found in the this thread: "+Thread.currentThread().getName());
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		
		
	}
	
}