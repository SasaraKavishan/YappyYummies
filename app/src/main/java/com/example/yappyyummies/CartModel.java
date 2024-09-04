package com.example.yappyyummies;

public class CartModel {

    int cartId ;
    int userId ;
    int productId ;
    int quantity ;

    public CartModel (int cartId,int userId,int productId,int quantity){

        this.cartId = cartId ;
        this.userId = userId ;
        this.productId = productId ;
        this.quantity = quantity ;

    }
    public int getCartId() {return cartId;}

    public int getUserId() {
        return userId;
    }

    public int getProductId() {
        return productId;
    }
    public int getQuantity() {
        return quantity;
    }
}
