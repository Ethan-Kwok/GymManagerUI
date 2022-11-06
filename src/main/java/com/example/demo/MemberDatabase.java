package com.example.demo;

/**
 * Database containing all Member objects in an array.
 * The size represents the number of Members in the database.
 * @author David Ma, Ethan Kwok
 */
public class MemberDatabase {
    private Member[] mlist;
    private int size;

    public static final int INITIAL_CAPACITY = 4;
    public static final int GROWTH_RATE = 4;
    public static final int NOT_FOUND = -1;

    public static final int SORT_BY_NAME = 0;
    public static final int SORT_BY_EXPIRATION = 1;
    public static final int SORT_BY_LOCATION = 2;

    /**
     * Creates a MemberDatabase object with an empty array containing no Members.
     */
    public MemberDatabase() {
        mlist = new Member[INITIAL_CAPACITY];
        size = 0; // number of members
    }

    /**
     * Finds the array index of a specified member.
     * @param member the Member to be searched for.
     * @return the index of the Member if they are in the database, or NOT_FOUND = -1 if they are not.
     */
    private int find(Member member) {
        for (int i = 0; i < size; i++) {
            if (member.equals(mlist[i])) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Getter method for the size of the memberDatabase.
     * @return the size of the memberDatabase
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Increases the size of the mlist array by GROWTH_RATE = 4 when the size is equal to the current mlist.length.
     * This ensures that there is always space in the database array for new Members. The size is increased by
     * creating a new array and copying the values into the new array.
     */
    private void grow() {
        Member[] newMlist = new Member[mlist.length + GROWTH_RATE];
        for (int i = 0; i < size; i++) {
            newMlist[i] = mlist[i];
        }
        mlist = newMlist;
    }

    /**
     * Adds a member to the database if they are not already in it.
     * The size value is increased accordingly, and grow() method is called if necessary.
     * @param member the Member to be added.
     * @return false if the Member is already in the database, true if the Member is successfully added.
     */
    public boolean add(Member member) {
        if (find(member) != NOT_FOUND) {
            return false;
        }

        mlist[size] = member;
        size++;

        if (size == mlist.length) {
            grow();
        }

        return true;
    }

    /**
     * Removes a member to the database if they are in it.
     * The size value is decreased accordingly.
     * @param member the Member to be removed.
     * @return false if the Member is not in the database, true if the Member is successfully removed.
     */
    public boolean remove(Member member) {
        int memberIndex = find(member);
        if (find(member) != NOT_FOUND) {
            for (int i = memberIndex; i < size; i++) {
                mlist[i] = mlist[i + 1];
            }
            size--;
            return true;
        }
        return false;
    }

    /**
     * Prints the Members in the database as is (that is, prints without sorting the array).
     * The Members are printed in the format given by toString() in the Member class. Note the check if the Member
     * database is empty is in displayMembers() in the GymManager class.
     */
    public String print() {
        String printOutput = "";
        for (int i = 0; i < size; i++) {
            if (mlist[i] != null) {
                printOutput += mlist[i].toString() + "\n";
            }
        }
        return printOutput;
    }

    /**
     * Sorts the array by location using the quickSort algorithm and then prints the Members in the database.
     * Location is sorted first alphabetically by county and then least to greatest by zipcode. If the database is
     * empty, it will print "Member database is empty!".
     */
    public String printByCounty() {
        String printOutput = "";

        int sortType = SORT_BY_LOCATION;
        quickSort(0, size-1, sortType);

        printOutput += "-list of members sorted by county and zipcode-\n";
        printOutput += print();
        printOutput += "-end of list-\n";
        return printOutput;
    }

    /**
     * Sorts the array by expiration date using the quickSort algorithm and then prints the Members in the database.
     * Expiration date is sorted from earliest to latest. If the database is empty, it will print "Member database
     * is empty!".
     */
    public String printByExpirationDate() {
        String printOutput = "";

        int sortType = SORT_BY_EXPIRATION;
        quickSort(0, size-1, sortType);

        printOutput += "-list of members sorted by membership expiration date-\n";
        printOutput += print();
        printOutput += "-end of list-\n";
        return printOutput;
    }

    /**
     * Sorts the array by name using the quickSort algorithm and then prints the Members in the database.
     * Name is sorted first alphabetically last name then first name. If the database is empty, it will print
     * "Member database is empty!".
     */
    public String printByName() {
        String printOutput = "";

        int sortType = SORT_BY_NAME;
        quickSort(0, size-1, sortType);

        printOutput += "-list of members sorted by last name, and first name-\n";
        printOutput += print();
        printOutput += "-end of list-\n";
        return printOutput;
    }


    /**
     * Recursive algorithm for the quickSort sorting method.
     * @param lowerBounds index for the lower bounds of each partition.
     * @param upperBounds index for the upper bounds of each partition.
     * @param sortType arbitrary integer representing which one of the three sorting methods is to be used.
     */
    private void quickSort(int lowerBounds, int upperBounds, int sortType) {
        if (lowerBounds < upperBounds) {
            int index = partition(lowerBounds, upperBounds, sortType);
            quickSort(lowerBounds, index - 1, sortType);
            quickSort(index + 1, upperBounds, sortType);
        }
    }

    /**
     * Partitions created by each call of the recursive quickSort() algorithm that swaps Members according to the
     * sorting method.
     * @param lowerBounds index for the lower bounds of sub-array created as a partition.
     * @param upperBounds index for the upper bounds of sub-array created as a partition.
     * @param sortType arbitrary integer representing which one of the three sorting methods is to be used.
     * @return the new pointer to the low index pivot that is used as an index for the next quickSort() method call.
     */
    private int partition(int lowerBounds, int upperBounds, int sortType) {
        Member pivot = mlist[upperBounds];
        int lowPtr = lowerBounds - 1;
        for (int i = lowerBounds; i < upperBounds; i++) {

            if (sortType == SORT_BY_NAME) {
                if (mlist[i].compareTo(pivot) <= 0) {
                    lowPtr++;
                    swap(lowPtr, i);
                }
            }

            if (sortType == SORT_BY_EXPIRATION) {
                if (mlist[i].getExpire().compareTo(pivot.getExpire()) <= 0) {
                    lowPtr++;
                    swap(lowPtr, i);
                }
            }

            if (sortType == SORT_BY_LOCATION) {
                if (mlist[i].compareLocation(pivot) <= 0) {
                    lowPtr++;
                    swap(lowPtr, i);
                }
            }

        }

        swap(lowPtr + 1, upperBounds);
        return lowPtr + 1;
    }

    /**
     * Helper method for the partition() method that swaps two Members in the database array.
     * @param first database array index of the first Member.
     * @param second database array index of the second Member.
     */
    private void swap(int first, int second) {
        Member temp = mlist[first];
        mlist[first] = mlist[second];
        mlist[second] = temp;
    }

    /**
     * Helper method to check if the database mlist is empty.
     * @return true if the database is empty, false if it is not.
     */
    public boolean isEmpty() {
        if (size != 0) {
            return false;
        }
        return true;
    }

    /**
     * Getter method to return the Member in the database.
     * @param member Member to be returned.
     * @return the Member if they are in the database, or null if they are not.
     */
    public Member getMember(Member member) {
        for (int i = 0; i < size; i++) {
            if (member.equals(mlist[i])) {
                return mlist[i];
            }
        }
        return null;
    }

    /**
     * Getter method to return the Member in the database.
     * @param index index of the Member in the member database.
     * @return the Member if they exist at the mlist[index], null if there is no member there.
     */
    public Member getMember(int index) {
        if (index >= size || index < 0) return null;
        return mlist[index];
    }

}