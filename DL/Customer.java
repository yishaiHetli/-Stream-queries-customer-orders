package DL;


import static java.lang.Long.parseLong;

public class Customer {
    private long id;
    private String name;
    private int tier;

    public Customer(String customerInfo) {
        String[] data = customerInfo.split(" ");
        try {
            setId(parseLong(data[1]));
            setName(data[3]);
            setTier(Integer.parseInt(data[5]));
        } catch (TypeNotPresentException e) {
            e.printStackTrace();
        }
    }

    public Customer(long Cid, String Cname, int Ctier) {
        setId(Cid);
        setName(Cname);
        setTier(Ctier);
    }

    public String toString() {
        return "customer: " + getId() + " name: " + getName() + " tier: " + getTier() + "\n";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        if (tier < 1 || tier > 3) {
            throw new IllegalArgumentException("Health must be between 1 and 3, inclusive");
        } else {
            this.tier = tier;
        }
    }
}
