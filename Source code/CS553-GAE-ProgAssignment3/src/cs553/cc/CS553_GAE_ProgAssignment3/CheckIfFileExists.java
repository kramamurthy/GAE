package cs553.cc.CS553_GAE_ProgAssignment3;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.channels.Channels;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileReadChannel;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class CheckIfFileExists extends HttpServlet
{
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	 public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	 {
		 String fileNameInBlob = null;
		 long startTime,endTime;
		 startTime=System.currentTimeMillis();
		 
		 String fileNameToSearch = req.getParameter("SearchFile"); 		   
		 res.getWriter().println("The file name to be searched is : " + fileNameToSearch);
		 		 
		 MemcacheService mService = MemcacheServiceFactory.getMemcacheService();
	     AppEngineFile cacheFile;
		 cacheFile = (AppEngineFile) mService.get(fileNameToSearch);
		 Boolean filePresent=false;
		 if(cacheFile != null)
		 { 
			 res.getWriter().println("File "+fileNameToSearch+" is present in the cache");
		 }
		 else
		 { 
			 res.getWriter().println("File "+fileNameToSearch+" is not present in the cache");
			 List<BlobInfo> dataBlob = new LinkedList<BlobInfo>();
		     Iterator<BlobInfo> blobIterator = new BlobInfoFactory().queryBlobInfos();
		     while(blobIterator.hasNext())
		    	 dataBlob.add(blobIterator.next());
		        
		        res.setContentType("text/plain");
		        int i;
		        
		        for(i=0;i<dataBlob.size();i++)
		        {
		        	
		        	fileNameInBlob = dataBlob.get(i).getFilename();
		        	
		        	if(fileNameInBlob.equals(fileNameToSearch))
		        	{
		        		filePresent=true;
		   			 	
		   			 	break;
		        	}
		        	
		        	else
		        	{
		        		filePresent=false;
		   			 	
		        	}
		        }
		        if(filePresent)
				 {
					 res.getWriter().println("File "+fileNameToSearch+" is present in the blob store");
				 }
				 else
				 {
					 res.getWriter().println("File "+fileNameToSearch+" is not present in the blob store");
				 }
		 }
		 endTime=System.currentTimeMillis();
		 
		 res.getWriter().println("Time Taken for the operation: "+(endTime-startTime)+" ms");
	}
}