package cs553.cc.CS553_GAE_ProgAssignment3;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class memCacheElements extends HttpServlet
{
	public void doPost(HttpServletRequest req,HttpServletResponse resp) throws IOException
	{
		MemcacheService mService = MemcacheServiceFactory.getMemcacheService();
		long noOfItems=mService.getStatistics().getItemCount();
		
		resp.setContentType("text/plain");
		resp.getWriter().println("Total number of elements in memcache is "+noOfItems);
	}
}
