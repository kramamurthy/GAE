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
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.NoFixedFacet;

public class BlobStorageSize extends HttpServlet 
{
	public void doPost(HttpServletRequest req,HttpServletResponse resp) throws IOException
	{
		List<BlobInfo> blobReader=new LinkedList<BlobInfo>();
		Iterator<BlobInfo> blobIterator = new BlobInfoFactory().queryBlobInfos();
		while(blobIterator.hasNext())
			blobReader.add(blobIterator.next());
		long noOfBytes = 0;
		for(int i=0;i<blobReader.size();i++)
		{
			noOfBytes += blobReader.get(i).getSize();
		}
		resp.setContentType("text/plain");
		resp.getWriter().println("The total storage size of a blob store is "+noOfBytes+" or"+(noOfBytes/(1024*1024))+" MB");
	}
}
