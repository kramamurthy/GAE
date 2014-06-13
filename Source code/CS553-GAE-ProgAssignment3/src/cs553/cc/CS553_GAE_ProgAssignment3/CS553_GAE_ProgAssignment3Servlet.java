package cs553.cc.CS553_GAE_ProgAssignment3;

import java.io.IOException;
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
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;


@SuppressWarnings("serial")
public class CS553_GAE_ProgAssignment3Servlet extends HttpServlet 
{
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    private MemcacheService cache = MemcacheServiceFactory.getMemcacheService();
    private AppEngineFile file;
    
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException 
	{
		
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	{
		int i;
    	Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
        List<BlobKey> blobKey = blobs.get("Upload");
             
        
        List<BlobInfo> blobToRead = new LinkedList<BlobInfo>();
        Iterator<BlobInfo> iterator = new BlobInfoFactory().queryBlobInfos();
        
        res.getWriter().println("Files Uploaded to blob successfully");
        
        
    	while(iterator.hasNext())
    		blobToRead.add(iterator.next()); 
    
    	
    	for(i=0;i<blobToRead.size();i++)
    	{
    		FileService fileService = FileServiceFactory.getFileService();
    		if(blobToRead.get(i).getSize() <= 104448){
    			this.file = fileService.getBlobFile(blobToRead.get(i).getBlobKey());
    			this.cache.put(blobToRead.get(i).getFilename(), this.file); 
    		}
    	}
    	res.getWriter().println("Files uploaded to cache successfully");
	}
	
}
