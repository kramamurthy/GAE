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

//Removes all the items from memcache and blob
public class RemoveAll extends HttpServlet
{
	public void doPost(HttpServletRequest req,HttpServletResponse resp) throws IOException
	{
		MemcacheService mService=MemcacheServiceFactory.getMemcacheService();
		//to check if memcache has any items
		if(mService.getStatistics().getItemCount()>0)
		{
			mService.clearAll();
			resp.getWriter().println("Cache cleared");
		}
		else
		{
			resp.getWriter().println("No items in cache to be cleared.");
		}
		
		List<BlobInfo> blobReader = new LinkedList<BlobInfo>();
		Iterator<BlobInfo> blobIterator = new BlobInfoFactory().queryBlobInfos();
		BlobstoreService bService=BlobstoreServiceFactory.getBlobstoreService();
		while(blobIterator.hasNext())
			blobReader.add(blobIterator.next());
		//to check if blobstore has any items.
		if(!blobReader.isEmpty())
		{
			for(int i=0;i<blobReader.size();i++)
	        {
				bService.delete(blobReader.get(i).getBlobKey());
	        }
		
			resp.getWriter().println("All items in blob are deleted");
		}
		else
		{
			resp.getWriter().println("No items in blob to be deleted");
		}
		
	}
}
