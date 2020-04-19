package etat_porte;

public class StateDoorClosed implements StateDoor{


	@Override
	public void opening() {
		System.out.println("Ouverture de la porte ....");
	}

	@Override
	public void closing() {
		System.out.println("Impossible car la porte est d�ja ferm�e");
		
	}

	@Override
	public void pause() {
		System.out.println("Impossible car la porte est d�ja ferm�e");
	}

	@Override
	public void closed() {
		System.out.println("Impossible car la porte est d�ja ferm�e");		
	}

	@Override
	public void opened() {
		System.out.println("Impossible car la porte est d�ja ferm�e");		
	}

	@Override
	public void blocked() {
		System.out.println("Impossible car la porte est d�ja ferm�e");		
	}

	@Override
	public void recovery() {
		System.out.println("Impossible car la porte est d�ja ferm�e");				
	}
	
	@Override
	public void getState() {
		System.out.println("Etat porte ferm�e");
		
	}

	@Override
	public void openedPartial() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closedPartial() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void PartialClosing() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void PartialOpening() {
		// TODO Auto-generated method stub
		
	}

}
