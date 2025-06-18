<%@ page import="java.util.*, model.ProductVariant" %>
<%
    List<ProductVariant> products = (List<ProductVariant>) request.getAttribute("products");
%>
<html>
<head><title>Products</title></head>
<body>
    <h2>All Products</h2>
    <div style="display:flex; flex-wrap:wrap;">
    <%
        for (ProductVariant p : products) {
    %>
        <div style="border:1px solid black; padding:10px; margin:10px; width: 200px;">
            <img src="<%= p.getImageUrl() %>" width="150" height="150" alt="<%= p.getProductName() %>"><br>
            <b><%= p.getProductName() %></b><br>
            Color: <%= p.getColour() %><br>
            Size: <%= p.getSize() %><br>
            ₹<%= p.getPrice() %><br>
            Stock: <%= p.getStockQuantity() %>
        </div>
    <%
        }
    %>
    </div>
</body>
</html>
