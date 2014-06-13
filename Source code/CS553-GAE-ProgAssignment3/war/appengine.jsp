<!-- // file index.jsp -->

<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>


<%
    BlobstoreService bService = BlobstoreServiceFactory.getBlobstoreService();
%>


<html>
    <head>
        <title>Upload Test</title>
    </head>
    <body>
    <script type="text/javascript">
    	function uploadFile() 
    	{	
    		if (window.File && window.FileList) 
    		{      
      			var filesToBeUploaded = document.getElementById('Upload').files;
      			document.getElementById('n').value=filesToBeUploaded.length;      
      		    this.uploadTime();
      			document.getElementById('formUpload').submit();
      		}
      		else
      		{
    			this.uploadTime();
    			document.getElementById('formUpload').submit();
    		}	
  		}
  		function uploadTime()
  		{  	  
  	  		var time = new Date().getTime();
      		document.getElementById('time').value=time;      
  		}
	</script>
	<form id="formUpload" enctype="multipart/form-data" method="post" action="<%= bService.createUploadUrl("/fileupload") %>">
   		<p>Browse files to be uploaded. Multiple files can be uploaded to app engine</p>
    	<input type="text" name="number" id="n" value="1" hidden="hidden"/>
   		<input type="text" name="time" id="time" value="1" hidden="hidden"/>
   		<input type="file" name="Upload" id="Upload" multiple />
   		<input type="button" onclick="uploadFile();" value="Upload Files" />
	</form>
	<form action="/ListAllFiles" method="post"> 
    	<p>Click to list all the files : </p>
        <input type="Button" name="ListFiles">
        <input id="submit" type="submit" value="List" >
     </form>
     <form action="/CheckIfFileExists" method="post">
        <p>Enter the filename to be searched : </p> 
        <input type="text" name="SearchFile">
        <input id="submit" type="submit" value="Search" >
      </form>
      <form action="/FileContent" method="post"> 
      	<p>Enter the filename to display its contents : </p>
        <input type="text" name="FileToDisplay">
        <input id="submit" type="submit" value="Display Contents" >
      </form>
      <form action="/RemoveFile" method="post"> 
      	<p>Enter the filename to be deleted : </p>
        <input type="text" name="FileToRemove">
        <input id="submit" type="submit" value="Remove" >
      </form>
      <form action="/MemCacheUtilization" method="post"> 
      	<p>Click to view the utilized memcache size </p>
        <input id="submit" type="submit" value="MemCacheUtilization" >
      </form>
      <form action="/RemoveCache" method="post"> 
      	<p>Click to delete all the key value pairs in memcache </p>
        <input id="submit" type="submit" value="RemoveCache" >
      </form>
      <form action="/memCacheElements" method="post"> 
      	<p>Click to view the number of elements in the mem cache </p>
        <input id="submit" type="submit" value="MemElements" >
      </form>
      <form action="/BlobStorageSize" method="post"> 
      	<p>Click to view the storage placed utilized in the blob store. </p>
        <input id="submit" type="submit" value="BlobUtilization" >
      </form>
      <form action="/BlobStoreElements" method="post"> 
      	<p>Click to view the number of elements in the blob store  </p>
        <input id="submit" type="submit" value="BlobElements" >
      </form>
      <form action="/RemoveAll" method="post"> 
      	<p>Click to delete all the items from the cache and blob  </p>
        <input id="submit" type="submit" value="RemoveAll" >
      </form>
      </body>
</html>