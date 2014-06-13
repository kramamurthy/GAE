package cs553.cc.CS553_GAE_ProgAssignment3;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;

public class BlobStoreElements extends HttpServlet
{
	public void doPost(HttpServletRequest req,HttpServletResponse resp) throws IOException
	{
		List<BlobInfo> blobReader=new LinkedList<BlobInfo>();
		Iterator<BlobInfo> blobIterator = new BlobInfoFactory().queryBlobInfos();
		while(blobIterator.hasNext())
			blobReader.add(blobIterator.next());
		resp.setContentType("text/plain");
		
		resp.getWriter().println("Total number of elements in blob store is "+blobReader.size());
	}
}
