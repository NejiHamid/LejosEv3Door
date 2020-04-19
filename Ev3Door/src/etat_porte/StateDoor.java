package etat_porte;

public interface StateDoor {
	
	public void opening();
	public void closing();
	public void pause();
	public void closed();
	public void opened();
	public void openedPartial();
	public void closedPartial();
	public void PartialClosing();
	public void PartialOpening();
	public void blocked();
	public void recovery();
	public void getState();

}
