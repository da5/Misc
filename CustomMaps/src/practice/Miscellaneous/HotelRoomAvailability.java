package practice.Miscellaneous;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by dasarindam on 4/21/2017.
 *
 hotels.txt
 Hotel1, 10
 Hotel2, 5
 Hotel3, 8

 bookings.txt
 Hotel1, 2016-12-20, 2016-12-23
 Hotel1, 2016-12-20, 2016-12-28
 Hotel1, 2016-12-21, 2016-12-24
 Hotel1, 2016-12-20, 2016-12-23
 Hotel1, 2016-12-20, 2016-12-23
 Hotel1, 2016-12-20, 2016-12-23
 Hotel1, 2016-12-20, 2016-12-23
 Hotel1, 2016-12-20, 2016-12-23
 Hotel1, 2016-12-20, 2016-12-23
 Hotel1, 2016-12-20, 2016-12-23
 Hotel2, 2016-12-20, 2016-12-23
 Hotel2, 2016-12-20, 2016-12-28
 Hotel3, 2016-12-21, 2016-12-24
 Hotel2, 2016-12-20, 2016-12-23
 */

class Hotel{
    String name;
    Integer rooms;

    public Hotel(String name, Integer rooms) {
        this.name = name;
        this.rooms = rooms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRooms() {
        return rooms;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }
}
class Booking{
    Hotel hotel;
    Date startDate;
    Date endDate;

    public Booking(Hotel hotel, Date startDate, Date endDate) {
        this.hotel = hotel;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}

public class HotelRoomAvailability {

    public static void main(String args[]){
        Map<String, Hotel> hotels = readHotels("C:\\Users\\dasarindam\\Documents\\Mine\\Documents\\hotels.txt");
        List<Booking> bookings = readBookings(hotels, "C:\\Users\\dasarindam\\Documents\\Mine\\Documents\\bookings.txt");
        Map<Date, Set<String>> availabilityMap = createAvailabilityMap(bookings, hotels.keySet());


    }

    private static Map<Date, Set<String>> createAvailabilityMap(List<Booking> bookings, Set<String> globalIdHotelSet){
        Map<Date, Set<String>> dateToHotelAvailabilityMap = new HashMap<>();
        Map<String, Map<Date, Integer>> hotelToDateRoomCount = new HashMap<>();
        for(Booking booking: bookings){
            Hotel hotel = booking.getHotel();
            if(!hotelToDateRoomCount.containsKey(hotel.getName())){
                hotelToDateRoomCount.put(hotel.getName(), new HashMap<>());
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(booking.getStartDate());
            Map<Date, Integer> dateToRoomCount = hotelToDateRoomCount.get(hotel.getName());
            for (;!calendar.getTime().after(booking.getEndDate()); calendar.add(Calendar.DATE, 1)){
                if(!dateToRoomCount.containsKey(calendar.getTime())){
                    dateToRoomCount.put(calendar.getTime(), hotel.getRooms());
                }
                Integer rooms = dateToRoomCount.get(calendar.getTime());
                dateToRoomCount.put(calendar.getTime(), rooms - 1);
            }
        }
        for(Map.Entry<String, Map<Date, Integer>> entry: hotelToDateRoomCount.entrySet()){
            String hotelId = entry.getKey();
            for(Map.Entry<Date, Integer> entry1: entry.getValue().entrySet()){
                if(!dateToHotelAvailabilityMap.containsKey(entry1.getKey())){
                    dateToHotelAvailabilityMap.put(entry1.getKey(), new HashSet<>(globalIdHotelSet));
                }
                if(entry1.getValue() == 0){
                    dateToHotelAvailabilityMap.get(entry1.getKey()).remove(hotelId);
                }
            }
        }
        return dateToHotelAvailabilityMap;
    }
    private static Map<String, Hotel> readHotels(String filePath){
        String line;
        BufferedReader bufferedReader;
        Map<String, Hotel> hotels = new HashMap<>();
        try{
            bufferedReader = new BufferedReader(new FileReader(filePath));
            while((line = bufferedReader.readLine())!=null){
                String[] components = line.split(",");
                hotels.put(components[0].trim(),
                        new Hotel(components[0].trim(), Integer.parseInt(components[1].trim())));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return hotels;
    }

    private static List<Booking> readBookings(Map<String, Hotel> hotels, String filePath){
        String line;
        BufferedReader bufferedReader;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<Booking> bookings = new ArrayList<>();
        try{
            bufferedReader = new BufferedReader(new FileReader(filePath));
            while((line = bufferedReader.readLine())!=null){
                String[] components = line.split(",");
                bookings.add(
                        new Booking(
                                hotels.get(components[0].trim()),
                                dateFormat.parse(components[1].trim()),
                                dateFormat.parse(components[2].trim())
                        )
                );
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (ParseException e){
            e.printStackTrace();
        }
        return bookings;
    }


}
