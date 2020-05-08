package database.models;

public class Installation {
    private String addres;
    private String routherNumber;
    private String serviceType;
    private int serviceid;
    private int userid;

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }

    public String getRoutherNumber() {
        return routherNumber;
    }

    public void setRoutherNumber(String routherNumber) {
        this.routherNumber = routherNumber;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public int getServiceid() {
		return serviceid;
	}

	public void setServiceid(int serviceid) {
		this.serviceid = serviceid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public Installation(String addres, String routherNumber, String serviceType,int serviceid,int userid) {
        this.addres = addres;
        this.routherNumber = routherNumber;
        this.serviceType = serviceType;
        this.serviceid=serviceid;
        this.userid=userid;
    }
}
