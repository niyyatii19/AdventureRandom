package com.company;

import java.io.*;
import java.util.*;

public class Location implements Map<Integer, Locations> {

    private static Map<Integer, Locations> locationsMap = new LinkedHashMap<>();
    private static Map<Integer, IndexPositions>indexPositionsMap = new LinkedHashMap<>();
    private static RandomAccessFile ra;

    public static void main(String[] args) throws IOException {
        try(RandomAccessFile rao = new RandomAccessFile("locations_random.dat", "rwd")){
            rao.writeInt(locationsMap.size());
            int indexSize = (int) (locationsMap.size() *3 * Integer.SIZE);
            int locationStart = (int) (indexSize + rao.getFilePointer() + Integer.SIZE) ;
            rao.writeInt(locationStart);
            long indexStart = rao.getFilePointer();
            int startPointer = locationStart;
            rao.seek(startPointer);

            for(Locations location : locationsMap.values()){
                rao.writeInt(location.getLocationId());
                rao.writeUTF(location.getDescription());
                StringBuilder builder = new StringBuilder();
                for(String exits: location.getExits().keySet()) {
                    if (exits.equalsIgnoreCase("Q")) {
                        builder.append(exits);
                        builder.append(",");
                        builder.append(location.getExits().get(exits));
                        builder.append(",");
                    }
                }
                rao.writeUTF(builder.toString());
                IndexPositions index = new IndexPositions(startPointer, (int) (startPointer - rao.getFilePointer()));
                indexPositionsMap.put(startPointer, index);
                startPointer= (int) rao.getFilePointer();
            }
            rao.seek(indexStart);
            for(Integer locationId: indexPositionsMap.keySet()){
                rao.writeInt(locationId);
                rao.writeInt(indexPositionsMap.get(locationId).getStartByte());
                rao.writeInt(indexPositionsMap.get(locationId).getLength());
            }
        }



    }




   static {
       try{
           ra = new RandomAccessFile("locations_random.dat", "rwd");
           int numLocation = ra.readInt();
           long startPointer = ra.readInt();

           while(ra.getFilePointer()< startPointer){
               int locationId = ra.readInt();
               int locationStart = ra.readInt();
               int locationLength = ra.readInt();
               IndexPositions record = new IndexPositions(locationStart, locationLength);
               indexPositionsMap.put(locationId, record);
           }
       }catch (IOException e){
           System.out.println("IOException: "+ e.getMessage());
       }
    }


   public Locations getlocation(int locationId) throws IOException{
        IndexPositions index = indexPositionsMap.get(locationId);
        ra.seek(index.getStartByte());
        int id = ra.readInt();
        String description = ra.readUTF();
        String exits = ra.readUTF();
        String[] exitsArray = exits.split(",");
        Locations location = new Locations(locationId , description, null);
        if(locationId!= 0){
            for(int i=0; i< exitsArray.length; i++){
                System.out.println("ExitsArray []"+ exitsArray[i]);
                System.out.println("Exits Array [i+1] "+ exitsArray[i+1]);
                String direction = exitsArray[i];
                int destination = Integer.parseInt(exitsArray[i++]);
                location.addExits(direction, destination);
            }
        }
        return location;
    }

    @Override
    public int size() {
        return locationsMap.size();
    }

    @Override
    public boolean isEmpty() {
        return locationsMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return locationsMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return locationsMap.containsValue(value);
    }

    @Override
    public Locations get(Object key) {
        return locationsMap.get(key);
    }

    @Override
    public Locations put(Integer key, Locations value) {
        return locationsMap.put(key, value);
    }

    @Override
    public Locations remove(Object key) {
        return locationsMap.remove(key);
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Locations> m) {

    }

    @Override
    public void clear() {
        locationsMap.clear();
    }

    @Override
    public Set<Integer> keySet() {
        return locationsMap.keySet();
    }

    @Override
    public Collection<Locations> values() {
        return locationsMap.values();
    }

    @Override
    public Set<Entry<Integer, Locations>> entrySet() {
        return locationsMap.entrySet();
    }

    public void close() throws IOException{
        ra.close();
    }
}





