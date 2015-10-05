package pl.surecase.eu;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

public class MyDaoGenerator
{

    public static Schema schema;

    public static void main(String args[]) throws Exception
    {
        schema = new Schema(1, "greendao");

        Entity product = schema.addEntity("GProduct");
        product.addLongProperty("id").primaryKey();
        product.addStringProperty("name");
        product.addStringProperty("description");
        product.addDoubleProperty("price");



        Entity customer  = schema.addEntity("Customer");
        customer.addLongProperty("id").primaryKey();
        customer.addStringProperty("name");
        customer.addStringProperty("email");
        customer.addStringProperty("phone");

        new DaoGenerator().generateAll(schema, args[0]);
    }


    // sample
    public static void gen1box_manyitems()
    {

        Entity box = schema.addEntity("Box");
        box.addIdProperty();
        box.addStringProperty("name");
        box.addIntProperty("slots");
        box.addStringProperty("description");

        Entity item = schema.addEntity("Item");
        Property itemId = item.addIdProperty().getProperty();
        item.addStringProperty("name");
        item.addIntProperty("quantity");

        Property boxId = item.addLongProperty("boxId").getProperty();
        ToMany boxToItem = box.addToMany(item, boxId);
        boxToItem.orderDesc(itemId);

    }


    // sample
    public static void gen1box1item()
    {

        Entity box = schema.addEntity("Box");
        box.addIdProperty();
        box.addStringProperty("name");
        box.addIntProperty("slots");
        box.addStringProperty("description");

        Entity item = schema.addEntity("Item");
        item.addIdProperty();
        item.addStringProperty("name");
        item.addIntProperty("quantity");

        Property itemId = box.addLongProperty("itemId").getProperty();
        box.addToOne(item, itemId);

    }
}
