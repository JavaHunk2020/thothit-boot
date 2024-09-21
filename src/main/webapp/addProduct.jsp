<%@page import="com.techtech.entity.ProductEntity"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Loading!!!!!!!!!!!!!</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
	<h1>Add Product Page</h1>
	<header style="height: 30px;background-color: yellow;">
	
	</header>
	<div class="container">	
		<img src="data:image/jpeg;base64,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"/>
		${message}
		<div class="form-group" style="width:50%">
		     <form action="addProduct" method="post">
			  	<label><b>Name</b></label>
			  	<input type="text" class="form-control" name="name"/>
			  	
			  				  	<label><b>Price</b></label>
			  	<input type="text" class="form-control" name="price" style="width: 200px;"/>
			  	
			  					<br/>
			  				  	<label><b> Category</b></label>
			  	                 <select class="form-control" name="category" style="width: 200px;">
			  	                      <option>Mobile</option>
			  	                      <option>Laptop</option>
			  	                      <option>Vehicle</option>
			  	                 </select>
			  	<br/>
			  	
			  				  	<label><b>Photo</b></label>
			  	           <input type="text" class="form-control" name="photo"/>
			  	
			  	    <button type="submit" class="btn btn-primary mt-2">Add Product!</button>
			  </form>
			  
			  <hr/>
		</div>
		<form action="searchProduct" method="get">
			        <input type="text" class="form-control" name="stext" style="width: 200px;display: inline;">
			  	    <button type="submit" class="btn btn-success">Search Product!</button>
			  	</form>
		
		<hr/>
		
		<img src="api/chart">
		
		<span style="color:red;font-size: 16px;font-weight: bold;"> ${message}</span>
		
		<table class="table table-bordered">
    <thead>
      <tr>
        <th>Pid</th>
        <th>
        <a href="sorting?attribute=name&orderBy=${orderBy}">
        Name
        </a>
        </th>
        <th>Price</th>
        <th> <a href="sorting?attribute=category&orderBy=${orderBy}">
        Category
        </a></th>
         <th>Photo</th>
          <th>Action</th>
      </tr>
    </thead>
    <tbody>
      <%
      //This is standard code to access the map
	   List<ProductEntity>  productList=(List<ProductEntity>)request.getAttribute("productList");
      if(productList==null){
    	  productList=new ArrayList<>();
      }
      int count=1;
      //Set<Entry<String,LocalDateTime> >
      for(ProductEntity item :productList) {
      %>
      <tr>
    <td><%=item.getPid()%></td>
        <td><%=item.getName()%></td>
        <td><%=item.getPrice()%></td>
        <td><%=item.getCategory()%></td>
        <td>
			<img alt="" src="<%=item.getPhoto()%>" style="height: 120px;">          
        </td>
         <td>
           <a href="deleteProduct?pid=<%=item.getPid()%>">
           <img style="height: 50px;" alt="" src="data:image/png;base64,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">
           </a>
         </td>
      </tr>
      
      <%} %>
    </tbody>
  </table>
		
		
	</div>
	
	
	
</body>
</html>