package cs553.cc.CS553_GAE_ProgAssignment3;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.channels.Channels;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileReadChannel;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class FileContent extends HttpServlet
{
	private BlobstoreService bService = BlobstoreServiceFactory.getBlobstoreService();
	 public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	 {
		 String fileName = null;
		 long startTime, endTime;
		 String fileContentToDisplay = req.getParameter("FileToDisplay");
		 res.getWriter().println("File conetent to be displayed : " + fileContentToDisplay);
		 startTime=System.currentTimeMillis();
		 //Find if the file is in memcache. If present there is no need to go to blobstore to get the data
		 MemcacheService mService = MemcacheServiceFactory.getMemcacheService();	        	
    	 AppEngineFile readableFile;
    	 readableFile = (AppEngineFile) mService.get(fileName);
    	 
    	 if(readableFile!=null)
    	 {
    		 FileService fService = FileServiceFactory.getFileService();
    		 res.getWriter().println("File "+fileName+" is present in cache");
    		 FileReadChannel readChannel = fService.openReadChannel(readableFile, false);
    		 BufferedReader reader = new BufferedReader(Channels.newReader(readChannel, "UTF8"));
    		 String line = null;
    		 res.getWriter().println("Contents of the file: ");
    		 
    		 while((line = reader.readLine()) != null)
    		 {        					
    			 	res.getWriter().println(line + "<br>");
    		 }
    		 readChannel.close();
    	 }
    	 else //Search for the file in blob
    	 {
    		 List<BlobInfo> blobToRead = new LinkedList<BlobInfo>();
    	     Iterator<BlobInfo> blobIterator = new BlobInfoFactory().queryBlobInfos();
    	     while(blobIterator.hasNext())
    	        	  blobToRead.add(blobIterator.next());
    	     res.setContentType("text/plain");
    	     Boolean filePresent=false;
    	     int i;
    	     for(i=0;i<blobToRead.size();i++)
    	     {
 	        	fileName = blobToRead.get(i).getFilename();
 	        	if(fileName.equals(fileContentToDisplay))
 	        	{   
 	        		FileService fService = FileServiceFactory.getFileService();
 	    	    	res.getWriter().println("File "+fileName+" is present in the blob store");
 	        		      		
 	        		readableFile = fService.getBlobFile(blobToRead.get(i).getBlobKey());
 	        		 		        	
 	        		 FileReadChannel readChannel = fService.openReadChannel(readableFile, false);
 	        		 BufferedReader reader = new BufferedReader(Channels.newReader(readChannel, "UTF8"));
 	        		 String line = null;
 	        		 res.getWriter().println("printing the contents : ");
 	        		 
 	        		 while((line = reader.readLine()) != null){ 
 	        			 	
 	        			 	res.getWriter().println(line);
 	        		 }
 	        		 
 	        		 readChannel.close(); 
 	        	}
 	        	
 	        	else
 	        	{
 	        		res.getWriter().println("File "+fileName+" is not present in the blob store");
 	        	}
 	        }
    	       	        
    	 }
    	 endTime=System.currentTimeMillis();
    	 res.getWriter().println("Time taken for the operation is "+(endTime-startTime)+" ms");
		 
	 }
}
