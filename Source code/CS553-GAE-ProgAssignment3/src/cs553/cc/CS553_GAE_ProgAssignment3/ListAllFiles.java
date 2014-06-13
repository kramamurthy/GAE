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


public class ListAllFiles extends HttpServlet
{
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	{
		List<BlobInfo> readBlob= new LinkedList<BlobInfo>();
        Iterator<BlobInfo> blobIterator = new BlobInfoFactory().queryBlobInfos();
        while(blobIterator.hasNext())
        	readBlob.add(blobIterator.next());
        
        res.setContentType("text/plain");
        
        for(int i=0;i<readBlob.size();i++)
        {
        	String fileName = readBlob.get(i).getFilename();
        	res.getWriter().println("File Name "+ i + ":" +  fileName);
        }    
    }
}
