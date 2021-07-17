package beanstudycafe_dto;

public class CafeDTO { // seq와 code 수정x

	private int seq;
	private int code;
	private String name = "guest";
	private String tel;
	private String mail;
	private String password;
	private int time;
	private double totStime;
	private double totRtime;
	private int payment;
	private int sit;
	private int room;
	private int locker;
	private double intime;
	private double outtime;

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getTime() {
		return time;
	}
	
	public void setTime(int time) {
		this.time = time;
	}

	public double getTotStime() {
		return totStime;
	}

	public void setTotStime(double totStime) {
		this.totStime = totStime;
	}

	public double getTotRtime() {
		return totRtime;
	}

	public void setTotRtime(double totRtime) {
		this.totRtime = totRtime;
	}

	public int getPayment() {
		return payment;
	}

	public void setPayment(int payment) {
		this.payment = payment;
	}

	public int getSit() {
		return sit;
	}

	public void setSit(int sit) {
		this.sit = sit;
	}

	public int getRoom() {
		return room;
	}

	public void setRoom(int room) {
		this.room = room;
	}

	public int getLocker() {
		return locker;
	}

	public void setLocker(int locker) {
		this.locker = locker;
	}

	public double getIntime() {
		return intime;
	}

	public void setIntime(double intime) {
		this.intime = intime;
	}

	public double getOuttime() {
		return outtime;
	}

	public void setOuttime(double outtime) {
		this.outtime = outtime;
	}

	@Override
	public String toString() {
		return tel;

	}

}