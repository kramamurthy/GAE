package cs553.cc.CS553_GAE_ProgAssignment3;

import javax.servlet.http.HttpServlet;

import java.io.IOException;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class RemoveFile extends HttpServlet
{
	private BlobstoreService bService = BlobstoreServiceFactory.getBlobstoreService();
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		String fileName=null;
		long startTime,endTime;
		String fileNameToRemove = req.getParameter("FileToRemove"); 		   
		res.getWriter().println("File to be removed" + fileNameToRemove);
		startTime=System.currentTimeMillis();
		List<BlobInfo> readBlob = new LinkedList<BlobInfo>();
        Iterator<BlobInfo> blobIterator = new BlobInfoFactory().queryBlobInfos();
        
        while(blobIterator.hasNext())
        	  readBlob.add(blobIterator.next());
        res.setContentType("text/plain");
        Boolean filePresent = false;
        int i;
        for(i=0;i<readBlob.size();i++)
        {
        	fileName = readBlob.get(i).getFilename();
        	
        	if(fileName.equals(fileNameToRemove))
        	{
        		filePresent = true;
        		break;
        	}
        	
        	else
        	{
        		filePresent = false;
        	}
        }
        
        if(filePresent)
        {
        	bService.delete(readBlob.get(i).getBlobKey());
        	res.getWriter().println("File "+fileNameToRemove+" deleted from the blob store");
        	
        	MemcacheService mService = MemcacheServiceFactory.getMemcacheService();        		                				
			mService.delete(fileName);
        		        	
        }
        else
    	{
    		res.getWriter().println("File "+fileNameToRemove+" is not found to be deleted");
    	}
        endTime=System.currentTimeMillis();
        res.getWriter().println("Time Taken for the operation is "+(endTime-startTime)+" ms");
                
	} 
	
}
