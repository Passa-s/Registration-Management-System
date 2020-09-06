package guest_Manage;


import java.io.Serializable;
import java.util.ArrayList;

import guest_Manage.Exception.AddGuestFailedException;


public class GuestsList implements Serializable {

	/**
	 * 
	 */
	
	private final int noOfSeats;
	private ArrayList<Guest> registeredGuests;
	private ArrayList<Guest> queuedGuests;
	
	private static final long serialVersionUID = 1L;
	
	public GuestsList(int noOfSeats) {
		this.noOfSeats = noOfSeats;
		this.registeredGuests = new ArrayList<Guest>();
		this.queuedGuests  = new ArrayList<Guest>();
	}
		
	// 1. Add a new guest 
	public int addGuest(Guest obj) throws NullPointerException, AddGuestFailedException {
		
		if (obj == null) {
	        throw new NullPointerException();
	    }

		
		boolean isInRegistered = this.isUsed(this.registeredGuests, obj);
		boolean isInQueued = this.isUsed(this.queuedGuests, obj);
		
		if (isInRegistered == true || isInQueued == true) {
			System.out.println(obj.getName() +  " you are already registered to the event.");
			return -1;
			
		} else if (this.getAvailableSeatsNo() > 0) {
			this.registeredGuests.add(obj);
			System.out.println(obj.getName() + ", congratulation! Your registration is confirmed. See you there!");
			return 0;
			
		} else if (this.getAvailableSeatsNo() == 0) {
			this.queuedGuests.add(obj);
			int position = this.queuedGuests.indexOf(obj) + 1;
			System.out.println("You are registered on the waiting list. Your entry is <" 
								+ position + ">. You will be notified if seat becomes available.");
			return position;
		}
		
		throw new Exception.AddGuestFailedException("Guest was not added to the list.");
	}
	
	// 2. Check registered guest
	public boolean checkIfSubscribed(Guest obj) {
		if (obj != null) {
			System.out.println("The person is not registered to the event");
			return true;
		} 
		System.out.println("The person is not registered to the event");
		return false;
	}
	// 3. Remove guest from list
	public boolean remove(Guest obj) {
		if (obj != null) {
			this.removeAdditRegistered(obj);
			System.out.println("Guest was deleted");
			return true;
		}
		System.out.println("Guest couldn't be deleted");
		return false;
	}
	
	// 4. Update guest's personal information
	public boolean update(Guest obj, String[] string) {
		// [0] is the string value, [1] is the string command from Main\getInfoForUpdate()
		if (obj == null ) {
			return false;
		}
		switch (string[1]) {
			case "lastName":
				if (this.searchByLastName(string[0]) == true) {
					System.out.println("Surname already in use. Please try again");
				} else if (string[0].equals("")) {
					System.out.println("No character inserted. Please try again");
				} else {
					obj.setLastName(string[0]);
					System.out.println("Persoana a fost actualizata");
					return true;
				}
				break;
			case "firstName":
				if (string[0].equals("")) {
					System.out.println("No character inserted. Please try again");
				} else {
					obj.setFirstName(string[0]);
					System.out.println("Guest was updated");
					return true;
				}
				break;
			case "email":
				if (this.searchByEmail(string[0]) == true) {
					System.out.println("E-mail already in use. Please try again");
				} else if (string[0].equals("")) {
					System.out.println("No character inserted. Please try again");
				} else {
					obj.setEmail(string[0]);
					System.out.println("Guest was updated");
					return true;
				}
				break;
			case "phoneNo":
				if (this.searchByPhoneNo(string[0]) == true) {
					System.out.println("Phone no already in use. Please try again");
				} else if (string[0].equals("")) {
					System.out.println("No character inserted. Please try again");
				} else {
					obj.setPhoneNo(string[0]);
					System.out.println("Guest was updated");
					return true;
				}
				break;
			default:
				System.out.println("The command is not valid");
				return false;
		}
		return false;
	}
	// 5. Get registered guests list
	public void printGuestsList() {
		if (this.getRegisteredGuestsNumber() <= 0) {
			System.out.println("Guest list empty...");
		} else {
			for (int i = 0; i < this.getRegisteredGuestsNumber(); i++) {
				System.out.println(i + 1 + ". Name: " + this.getRegisteredGuests().get(i).getName() 
									+ ", Email: " + this.getRegisteredGuests().get(i).getEmail() 
									+ ", Phone no: " + this.getRegisteredGuests().get(i).getPhoneNo());
			} 
		}
	}
	// 6. Get waiting list (queued guests)
	public void printWaitList() {
		if (this.getQueuedNumber() <= 0) {
			System.out.println("Waitlist empty...");
		} else {
			for (int i = 0; i < this.getQueuedNumber(); i++) {
				System.out.println(i + 1 + ". Nume:" + this.getQueuedGuests().get(i).getName() 
									+ ", Email: " + this.getQueuedGuests().get(i).getEmail() 
									+ ", Telefon: " + this.getQueuedGuests().get(i).getPhoneNo());
			}
		}
	}
	// 7. Get number of available seats
	public int getAvailableSeatsNo() {
		return this.noOfSeats - getRegisteredGuestsNumber();
	}
	// 8. Get number of guests in queue list
	public int getQueuedNumber() {
		return this.queuedGuests.size();
	}
	// 9. Get number of participants
	public int getRegisteredGuestsNumber() {
		return this.registeredGuests.size();
	}
	// 10. Get number of all registrations
	public int getAllRegistrations() {
		return getRegisteredGuestsNumber() + getQueuedNumber();
	}
	// 11. Partial search and print methods for registered guests
	public ArrayList<Guest> partialSearchReg(String search) {
		search = search.toLowerCase();
		ArrayList<Guest> participants = new ArrayList<Guest>();
		
		for (int i = 0; i < this.registeredGuests.size(); i++) {
			Guest registeredGuest = this.registeredGuests.get(i);
			if (registeredGuest.getName().toLowerCase().contains(search) ||
				registeredGuest.getEmail().toLowerCase().contains(search) ||
				registeredGuest.getPhoneNo().toLowerCase().contains(search)) {
				participants.add(registeredGuest);
			}
		}
		return participants;
	}
	public void printPartialSearchReg(ArrayList<Guest> participantsList) {
		if (participantsList.size() > 0) {
			System.out.println("Based on your search criteria, the following guests were found: ");
			for (int i = 0; i < participantsList.size(); i++) {
				System.out.println(i + 1 + ". Name:" + participantsList.get(i).getName() 
									+ ", Email: " + participantsList.get(i).getEmail() 
									+ ", Phone no: " + participantsList.get(i).getPhoneNo());
			}
		} else {
			System.out.println("No guests matched your search.");
		}
		
	}
	
	// ................................... Additional methods ...................................
	
	// Verification of used credentials (delegate to Guests class)
	private boolean isUsed(ArrayList<Guest> list, Guest obj) {
		for (int i = 0; i < list.size(); i++) {
			Guest registeredParticipant = list.get(i);
			if (registeredParticipant.isUsed(obj)){
				return true;
			}
		}
		return false;
	}
	
	// Additional remove method which checks and moves person from waiting list 
	public void removeAdditRegistered(Guest obj) {
		this.registeredGuests.remove(obj);
		
		if (this.queuedGuests.isEmpty() == false) {
			Guest movedPerson = this.queuedGuests.get(0);
			this.queuedGuests.remove(movedPerson);
			this.registeredGuests.add(movedPerson);
			System.out.println(movedPerson.getName() + ", congratulation! Your registration is confirmed. See you there!");
		}
	}
	// Get the lists of guests
	public ArrayList<Guest> getRegisteredGuests() {
		return this.registeredGuests;
	}
	
	public ArrayList<Guest> getQueuedGuests() {
		return this.queuedGuests;
	}

	// Method to search by name, email and phone number
	public boolean searchByName(String name) {
		for (int i = 0; i < this.registeredGuests.size(); i++) {
			Guest registeredGuest = this.registeredGuests.get(i);
			if (registeredGuest.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		for (int i = 0; i < this.queuedGuests.size(); i++) {
			Guest queuedParticipant = this.queuedGuests.get(i);
			if (queuedParticipant.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean searchByEmail(String email) {
		for (int i = 0; i < this.registeredGuests.size(); i++) {
			Guest registeredGuest = this.registeredGuests.get(i);
			if (registeredGuest.getEmail().equalsIgnoreCase(email)) {
				return true;
			}
		}
		for (int i = 0; i < this.queuedGuests.size(); i++) {
			Guest queuedGuest = this.queuedGuests.get(i);
			if (queuedGuest.getEmail().equalsIgnoreCase(email)) {
				return true;
			}
		}
		return false;
	}
		
	public boolean searchByPhoneNo(String phoneNo) {
		for (int i = 0; i < this.registeredGuests.size(); i++) {
			Guest registeredGuest = this.registeredGuests.get(i);
			if (registeredGuest.getPhoneNo().equalsIgnoreCase(phoneNo)) {
				return true;
			}
		}
		for (int i = 0; i < this.queuedGuests.size(); i++) {
			Guest queuedGuest = this.queuedGuests.get(i);
			if (queuedGuest.getPhoneNo().equalsIgnoreCase(phoneNo)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean searchByLastName(String name) {
		for (int i = 0; i < this.registeredGuests.size(); i++) {
			Guest registeredGuest = this.registeredGuests.get(i);
			if (registeredGuest.getLastName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		for (int i = 0; i < this.queuedGuests.size(); i++) {
			Guest queuedGuest = this.queuedGuests.get(i);
			if (queuedGuest.getLastName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}
	
	
	// Method to get guest by name, email, phone number 
	public Guest getGuestByName(String fullName) {
		for (int i = 0; i < this.registeredGuests.size(); i++) {
			Guest registeredGuest = this.registeredGuests.get(i);
			if (registeredGuest.getName().equalsIgnoreCase(fullName)) {
				return registeredGuest;
			}
		}
		for (int i = 0; i < this.queuedGuests.size(); i++) {
			Guest queuedGuest = this.queuedGuests.get(i);
			if (queuedGuest.getName().equalsIgnoreCase(fullName)) {
				return queuedGuest;
			}
		}
		System.out.println("The following guest, " + fullName + ", is not registered");
		return null;
	}
		
	public Guest getGuestByEmail(String email) {
		for (int i = 0; i < this.registeredGuests.size(); i++) {
			Guest registeredGuest = this.registeredGuests.get(i);
			if (registeredGuest.getEmail().equalsIgnoreCase(email)) {
				return registeredGuest;
			}
		}
		for (int i = 0; i < this.queuedGuests.size(); i++) {
			Guest queuedGuest = this.queuedGuests.get(i);
			if (queuedGuest.getEmail().equalsIgnoreCase(email)) {
				return queuedGuest;
			}
		} 
		System.out.println("The guest with the following e-mail " + email + " is not registered");
		return null;
	}
		
	public Guest getGuestByPhoneNo(String phoneNo) {
		for (int i = 0; i < this.registeredGuests.size(); i++) {
			Guest registeredGuest = this.registeredGuests.get(i);
			if (registeredGuest.getPhoneNo().equalsIgnoreCase(phoneNo)) {	
				return registeredGuest;
			}
		}
		for (int i = 0; i < this.queuedGuests.size(); i++) {
			Guest queuedGuest = this.queuedGuests.get(i);
			if (queuedGuest.getPhoneNo().equalsIgnoreCase(phoneNo)) {
				return queuedGuest;
			}
		} 
		System.out.println("The guest with the following phone no " + phoneNo + " is not registered");
		return null;
	}
		

}
