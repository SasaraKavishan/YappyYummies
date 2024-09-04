package com.example.yappyyummies;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlDatabaseHelper extends SQLiteOpenHelper {
    public SqlDatabaseHelper (Context context){
        super(context,"Yappy_Yummies.db",null,1);

    }
    @Override
    public void onCreate (SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users ("
                +"user_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"email TEXT UNIQUE NOT NULL,"
                +"password TEXT NOT NULL,"
                +"first_name TEXT NOT NULL,"
                +"last_name TEXT NOT NULL,"
                +"address TEXT,"
                +"city TEXT,"
                +"state TEXT,"
                +"postal_code TEXT,"
                +"country TEXT,"
                +"phone_number TEXT,"
                +"payment_method TEXT,"
                +"created_at DATETIME DEFAULT CURRENT_TIMESTAMP,"
                +"updated_at DATETIME DEFAULT CURRENT_TIMESTAMP);");

        db.execSQL("CREATE TABLE products ("
                +"product_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"name TEXT NOT NULL,"
                +"description TEXT NOT NULL,"
                +"brand TEXT NOT NULL,"
                +"type TEXT NOT NULL,"
                +"age TEXT NOT NULL,"
                +"price REAL NOT NULL,"
                +"image_url TEXT,"
                +"created_at DATETIME DEFAULT CURRENT_TIMESTAMP,"
                +"updated_at DATETIME DEFAULT CURRENT_TIMESTAMP);");

        db.execSQL("CREATE TABLE Reviews ("
                +"review_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"product_id INTEGER NOT NULL,"
                +"user_id INTEGER NOT NULL,"
                +"rating INTEGER CHECK(rating >= 1 AND rating <= 5) NOT NULL,"
                +"comment TEXT,"
                +"created_at DATETIME DEFAULT CURRENT_TIMESTAMP,"
                +"FOREIGN KEY (product_id) REFERENCES Products(product_id),"
                +"FOREIGN KEY (user_id) REFERENCES Users(user_id));");

        db.execSQL("CREATE TABLE Cart ("
                +"cart_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"user_id INTEGER NOT NULL,"
                +"product_id INTEGER NOT NULL,"
                +"quantity INTEGER NOT NULL,"
                +"created_at DATETIME DEFAULT CURRENT_TIMESTAMP,"
                +"FOREIGN KEY (user_id) REFERENCES Users(user_id),"
                +"FOREIGN KEY (product_id) REFERENCES Products(product_id));");

        db.execSQL("CREATE TABLE Orders ("
                +"order_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"user_id INTEGER NOT NULL,"
                +"total_amount REAL NOT NULL,"
                +"status TEXT NOT NULL DEFAULT 'Pending',"
                +"created_at DATETIME DEFAULT CURRENT_TIMESTAMP,"
                +"updated_at DATETIME DEFAULT CURRENT_TIMESTAMP,"
                +"FOREIGN KEY (user_id) REFERENCES Users(user_id));");

        db.execSQL("CREATE TABLE OrderItems ("
                +"order_item_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"order_id INTEGER NOT NULL,"
                +"product_id INTEGER NOT NULL,"
                +"quantity INTEGER NOT NULL,"
                +"price REAL NOT NULL,"
                +"FOREIGN KEY (order_id) REFERENCES Orders(order_id),"
                +"FOREIGN KEY (product_id) REFERENCES Products(product_id));");

        db.execSQL("CREATE TABLE EducationalContent ("
                +"content_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"title TEXT NOT NULL,"
                +"content_type TEXT NOT NULL,"
                +"content TEXT NOT NULL,"
                +"category TEXT NOT NULL,"
                +"created_at DATETIME DEFAULT CURRENT_TIMESTAMP);");

        db.execSQL("CREATE TABLE PaymentMethods ("
                +"id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"user_id INTEGER NOT NULL,"
                +"card_number TEXT NOT NULL,"
                +"expiry_date TEXT NOT NULL,"
                +"cardholder_name TEXT NOT NULL,"
                +"CVV TEXT   NOT NULL,"
                +"created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                +"updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                +"FOREIGN KEY (user_id) REFERENCES Users(id));");



        db.execSQL("INSERT INTO Products (name, description, brand, type, age, price, image_url)\n" +
                "VALUES\n" +
                "( 'Hills Science Diet Adult Chicken & Barley','Premium dry dog food with real chicken as the first ingredient, ideal for adult dogs.', 'Hills Science Diet', 'Dry Food', 'Adult', 49.99,  'https://th.bing.com/th/id/R.d05cbf34592f15254dbab1e5e215f0f1?rik=XttHrVyiIe8v3Q&pid=ImgRaw&r=0'),\n" +
                "('Blue Buffalo Wilderness Puppy Chicken','Grain-free, high-protein dry dog food with chicken, perfect for puppies.', 'Blue Buffalo', 'Dry Food', 'Puppy', 39.99,  'https://image.chewy.com/is/catalog/51753._AC_SL600_V1460478784_.jpg'),\n" +
                "( 'Royal Canin Size Health Nutrition Small Adult Formula','Dry dog food tailored for small breed dogs, with precise nutrition for a long life.', 'Royal Canin', 'Dry Food', 'Adult', 45.99,  'https://res.cloudinary.com/epetstore/image/upload/t_product-primary-image/47044/1.webp'),\n" +
                "( 'Purina Pro Plan Senior Wet Dog Food','Tender slices in gravy with real beef, designed to support healthy aging in senior dogs.', 'Purina Pro Plan', 'Wet Food', 'Senior', 24.99,  'https://images-na.ssl-images-amazon.com/images/I/51pKLSJr0pL.jpg'),\n" +
                "('Nutro Ultra Large Breed Adult','Dry dog food with a trio of proteins from chicken, lamb, and salmon, ideal for large breed adult dogs.', 'Nutro Ultra', 'Dry Food', 'Adult', 52.99,  'https://i.pinimg.com/736x/4d/0d/27/4d0d2709bee7737092b177a986aab196.jpg'),\n" +
                "( 'Wellness CORE Grain-Free Puppy','Grain-free, high-protein dry food with deboned chicken, turkey meal, and salmon oil for puppies.', 'Wellness', 'Dry Food', 'Puppy', 44.99,  'https://img.thepets.net/wp-content/uploads/wellness-core-natural-grain-free-reduced-fat-dry-dog-food-medium.jpg'),\n" +
                "( 'Merrick Grain-Free Senior Chicken','Grain-free dry dog food with chicken and sweet potatoes, great for senior dogs.', 'Merrick', 'Dry Food', 'Senior', 54.99,  'https://th.bing.com/th/id/OIP.ZiJsW9v1vmRUlRBDVnI6TgHaME?w=736&h=1200&rs=1&pid=ImgDetMain');\n");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
