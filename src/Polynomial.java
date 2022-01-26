/**
 * The Polynomial Class has many methods to support calculations between different polynomials.
 * It is implemented using a LinkedList.
 *
 * @author Andrew Dibble
 * @version 1.0
 */
public class Polynomial {
    
    // Fields
    private LinkedList terms; //the LinkedList that holds the polynomial

    // Constructors
    
    /**
     * The constructor for the Polynomial class which creates a new LinkedList
     */
    public Polynomial() {
        terms = new LinkedList();
    }

    // Methods

    /**
     * inserts an item into the polynomial in the correct position (ordered by exponent), if the exponent equals another exponent already in the list, it adds the two coefficients
     *
     * @param coefficient the coefficient of the literal
     * @param exponent    the exponent of the literal
     */
    public void insertTerm(int coefficient, int exponent) {
        if (coefficient == 0) {  //if coefficient is 0, literal will always be 0 so no need to add it
            return;
        }

        Literal literal = new Literal(coefficient, exponent); //creating a new literal with the specified coefficient and exponent parameters
        LinkedList.Iterator current = terms.iterator(); //creating an iterator at the first element

        while (current.hasNext()) {  //if there is another literal in the linkedList
            Literal currentLiteral = (Literal) current.getElement();  //getting the literal from the linked list

            if (exponent == currentLiteral.getExponent()) {  //if the two exponents are equal add the coefficients
                currentLiteral.setCoefficient(currentLiteral.getCoefficient() + coefficient);
                return;
            } else if (exponent < currentLiteral.getExponent()) {  //if the exponent is less than the current literal exponent in the list insert the term
                try {
                    ListNode node = current.getNode();
                    Literal lit = (Literal) node.getNext().getElement();  //getting the next element
                    if (exponent > lit.getExponent()) {  //if the element is bigger then the next term, insert
                        terms.insert(literal, current);
                        return;
                    }
                } catch (NullPointerException n) {  //catches null pointer if it is at the end of the list
                    terms.insert(literal, current);  //if at the end, insert
                    return;
                }
            }
            current.next();
        }
        LinkedList.Iterator pointer = terms.zeroth();
        terms.insert(literal, pointer);   //if the literal is the biggest in the list, add it to the front
    }

    /**
     * sets the given polynomial to 0 by setting the head node's next element to null
     */
    public void zeroPolynomial() {
        LinkedList.Iterator headIter = terms.zeroth();  //creates an iterator that points to the head of the list
        ListNode head = headIter.getNode();  //getting the head node
        head.setNext(null);    //setting the next element of head node to null so that list will be empty and thus will display 0
    }

    /**
     * negates every coefficient in the polynomial
     *
     * @return the new polynomial that is negated
     */
    public Polynomial negate() {
        LinkedList.Iterator current = terms.iterator();
        Polynomial poly = new Polynomial();
        while (current.hasNext()) {
            Literal lit = (Literal) current.getElement();
            lit.setCoefficient(-1 * lit.getCoefficient());
            current.next();
            poly.insertTerm(lit.getCoefficient(), lit.getExponent());
        }
        return poly;
    }

    /**
     * Adds two polynomials together
     *
     * @param polynomial the polynomial to add to the other polynomial
     * @return the sum
     */
    public Polynomial plus(Polynomial polynomial) {
        LinkedList.Iterator currentIter = terms.iterator(); //creating an iterator for polynomial that called the method (the current polynomial)
        LinkedList.Iterator addIter = polynomial.terms.iterator();  //iterator for parameter polynomial (the polynomial to be added)
        
        Polynomial poly = new Polynomial();  //creating a new polynomial
        LinkedList.Iterator polyIter = poly.terms.zeroth();  //creating a new iterator that iterates over a new LinkedList


        while (currentIter.hasNext() || addIter.hasNext()) { //while one of the polynomials has more Literals
            Literal currentLit;
            Literal addLit;
            if (addIter.getNode() == null) {  //checking if the iterator is null
                addLit = new Literal(0, Integer.MIN_VALUE);  //if so, make it as small as can be so other polynomial value is added
            } else {
                addLit = (Literal) addIter.getElement();  //getting the Literal from terms from new poly Iterator
            }

            if (currentIter.getNode() == null) {  //checking if the iterator is null
                currentLit = new Literal(0, Integer.MIN_VALUE); //if so, make it as small as can be so other polynomial value is added
            } else {
                currentLit = (Literal) currentIter.getElement();  //getting the Literal from terms from current Iterator
            }

            //comparing which term to insert first
            if (currentLit.getExponent() > addLit.getExponent()) {
                poly.terms.insert(new Literal(currentLit.getCoefficient(), currentLit.getExponent()), polyIter);  //inserting bigger term into polynomial
                currentIter.next();
            } else if (currentLit.getExponent() < addLit.getExponent()) {
                poly.terms.insert(new Literal(addLit.getCoefficient(), addLit.getExponent()), polyIter);  //inserting bigger term into polynomial
                addIter.next();
            } else { //combining the terms by adding them
                poly.terms.insert(new Literal(currentLit.getCoefficient() + addLit.getCoefficient(), currentLit.getExponent()), polyIter);  //combining the terms since the exponents are equal
                addIter.next();
                currentIter.next();
            }

            polyIter.next();
        }
        return poly;
    }

    /**
     * Subtracts two polynomials
     *
     * @param polynomial the polynomial to subtract from the other polynomial
     * @return the difference
     */
    public Polynomial minus(Polynomial polynomial) {
        LinkedList.Iterator currentIter = terms.iterator(); //creating an iterator for polynomial that called the method (the current polynomial)
        LinkedList.Iterator subIter = polynomial.terms.iterator();  //iterator for parameter polynomial (the polynomial to be subtracted)
        
        Polynomial poly = new Polynomial();  //creating a new polynomial
        LinkedList.Iterator polyIter = poly.terms.zeroth();  //creating a new iterator that iterates over a new LinkedList


        while (currentIter.hasNext() || subIter.hasNext()) { //while one of the polynomials has more Literals
            Literal currentLit;
            Literal subLit;
            if (subIter.getNode() == null) {  //checking if the iterator is null
                subLit = new Literal(0, Integer.MIN_VALUE);  //if so, make it as small as can be so other polynomial value is added
            } else {
                subLit = (Literal) subIter.getElement();  //getting the Literal from terms from new poly Iterator
            }

            if (currentIter.getNode() == null) {  //checking if the iterator is null
                currentLit = new Literal(0, Integer.MIN_VALUE); //if so, make it as small as can be so other polynomial value is added
            } else {
                currentLit = (Literal) currentIter.getElement();  //getting the Literal from terms from current Iterator
            }

            //comparing which term to insert first
            if (currentLit.getExponent() > subLit.getExponent()) {
                poly.terms.insert(new Literal(currentLit.getCoefficient(), currentLit.getExponent()), polyIter);  //inserting bigger term into polynomial
                currentIter.next();
            } else if (currentLit.getExponent() < subLit.getExponent()) { //making the term negative if it is the term being subtracted
                poly.terms.insert(new Literal(subLit.getCoefficient() * -1, subLit.getExponent()), polyIter);  //inserting bigger term into polynomial
                subIter.next();
            } else { //combining the terms by subtracting them
                poly.terms.insert(new Literal(currentLit.getCoefficient() - subLit.getCoefficient(), currentLit.getExponent()), polyIter);  //combining the terms since the exponents are equal
                subIter.next();
                currentIter.next();
            }

            polyIter.next();
        }
        return poly;
    }

    /**
     * Multiplies two polynomials together and returns the result as a Polynomial Object
     * @param polynomial  the polynomial to multiply with the current polynomial
     * @return a Polynomial Object of the result of the multiplication
     */
    public Polynomial times(Polynomial polynomial) {
        LinkedList.Iterator currentIter = terms.iterator(); //creating an iterator for polynomial that called the method (the current polynomial)
        LinkedList.Iterator timesIter = polynomial.terms.iterator();  //iterator for parameter polynomial (the polynomial to be subtracted)

        Polynomial poly = new Polynomial();  //creating a new polynomial

        while(currentIter.hasNext()){ // while the current polynomial has more terms
            Literal currentLit = (Literal) currentIter.getElement();
            while(timesIter.hasNext()){  //multiply each timesLit by the currentLit
                Literal timesLit = (Literal) timesIter.getElement();
                //inserting the result into the poly, used insertTerm here instead of insert because some terms after multiplying can have the same exponent so need to add them together if so
                poly.insertTerm(currentLit.getCoefficient() * timesLit.getCoefficient(),currentLit.getExponent() + timesLit.getExponent());
                timesIter.next();
            }
            currentIter.next();
            timesIter = polynomial.terms.iterator(); //reset the timesIter, so it can loop through all terms again
        }
        return poly;
    }

    /**
     * takes the derivative of a polynomial
     *
     * @return the derivative of the polynomial as a Polynomial object
     */
    public Polynomial derivative() {
        LinkedList.Iterator currentIter = terms.iterator(); //creating an iterator for polynomial that called the method

        Polynomial poly = new Polynomial();  //creating a new polynomial
        LinkedList.Iterator polyIter = poly.terms.zeroth();  //creating a new iterator that iterates over a new LinkedList

        while (currentIter.hasNext()) { //while there is another term in the current polynomial
            Literal lit = (Literal) currentIter.getElement();  //getting the Literal from terms

            poly.terms.insert(new Literal(lit.getCoefficient() * lit.getExponent(), lit.getExponent() - 1), polyIter);  //taking the derivative and inserting into new linkedList
            polyIter.next();
            currentIter.next();
        }
        return poly;
    }

    /**
     * gives the String to display for the A and B terms on the GUI screen
     *
     * @return a string that is used to display the polynomial
     */
    public String print() {
        StringBuilder poly = new StringBuilder();  //creating a new StringBuilder
        LinkedList.Iterator currentLit = terms.iterator(); //creating a new iterator that starts at the beginning of the list
        if (terms.isEmpty()) {   //if the linked list is empty display 0
            return "0";
        } else { //printing out the correct form of the Literal
            Literal lit = (Literal) currentLit.getElement();
            if (lit.getExponent() == 0) {
                poly.append(lit.getCoefficient());
            } else if (lit.getCoefficient() == 0) {
                poly.append("0");
            } else if (lit.getCoefficient() == 1 && lit.getExponent() == 1) {
                poly.append("x");
            } else if (lit.getCoefficient() == 1 && lit.getExponent() != 1) {
                poly.append("x^").append(lit.getExponent());
            } else if (lit.getExponent() == 1) {
                poly.append(lit.getCoefficient()).append("x");
            } else if (lit.getCoefficient() > 0 && lit.getExponent() > 0) {
                poly.append(lit.getCoefficient()).append("x^").append(lit.getExponent());
            } else if (lit.getCoefficient() < 0 && lit.getExponent() > 0) {
                poly.append(" ").append(lit.getCoefficient()).append("x^").append(lit.getExponent());
            } else if (lit.getCoefficient() > 0 && lit.getExponent() < 0) {
                poly.append(lit.getCoefficient()).append("x^(").append(lit.getExponent()).append(")");
            } else if (lit.getCoefficient() < 0 && lit.getExponent() < 0) {
                poly.append(" ").append(lit.getCoefficient()).append("x^(").append(lit.getExponent()).append(")");
            }

            currentLit.next();

            while (currentLit.hasNext()) {  //if there is more than one Literal, loop through until the iterator has processed all Literals
                lit = (Literal) currentLit.getElement();
                if (lit.getExponent() == 0 && lit.getCoefficient() > 0) {
                    poly.append(" +").append(lit.getCoefficient());
                } else if(lit.getExponent() == 0 && lit.getCoefficient()<0){
                    poly.append(lit.getCoefficient());
                } else if (lit.getCoefficient() == 1 && lit.getExponent() == 1) {
                    poly.append("+ x");
                } else if (lit.getCoefficient() == 1 && lit.getExponent() != 1) {
                    poly.append("x^").append(lit.getExponent());
                } else if (lit.getExponent() == 1 && lit.getCoefficient() > 0) {
                    poly.append(" + ").append(lit.getCoefficient()).append("x");
                } else if (lit.getExponent() == 1 && lit.getCoefficient() < 0) {
                    poly.append(lit.getCoefficient()).append("x");
                } else if (lit.getCoefficient() > 0 && lit.getExponent() > 0) {
                    poly.append(" + ").append(lit.getCoefficient()).append("x^").append(lit.getExponent());
                } else if (lit.getCoefficient() < 0 && lit.getExponent() > 0) {
                    poly.append(" ").append(lit.getCoefficient()).append("x^").append(lit.getExponent());
                } else if (lit.getCoefficient() > 0 && lit.getExponent() < 0) {
                    poly.append(" + ").append(lit.getCoefficient()).append("x^(").append(lit.getExponent()).append(")");
                } else if (lit.getCoefficient() < 0 && lit.getExponent() < 0) {
                    poly.append(" ").append(lit.getCoefficient()).append("x^(").append(lit.getExponent()).append(")");
                }

                currentLit.next();
            }
            return poly.toString();  //return the string representation of the polynomial
        }

    }
}
