class Functions extends App {
  //Helpers
  //time complexity O(n), n - list.length
  //space complexity O(1)
  private def rev[A](list: List[A]): List[A] = {
    @scala.annotation.tailrec
    def revIter[A](listToRev: List[A], list: List[A]): List[A] = {
      listToRev match {
        case Nil => list
        case _ => revIter(listToRev.tail, listToRev.head :: list)
      }
    }

    revIter(list, List())
  }

  //exercise 1
  //time complexity O(n),  n = listToMerge.length
  //space complexity O(1) - tail recursion, optimizes stack frames, gets rid of extra calls
  def split(listToMerge: List[Int]): (List[Int], List[Int]) = {
    @scala.annotation.tailrec
    def splitIter(source: List[Int], first: List[Int], second: List[Int]): (List[Int], List[Int]) = {
      if (source == Nil) (rev(first), rev(second))
      else if (source.head < 0)
        if (source.head % 2 != 0)
          splitIter(source.tail, source.head :: first, source.head :: second)
        else splitIter(source.tail, source.head :: first, second)
      else splitIter(source.tail, first, second)
    }

    splitIter(listToMerge, List(), List())
  }

  //exercise 2
  //time complexity O(n),  n = list.length
  //space complexity O(1)
  def length[A](list: List[A]): Int = {
    @scala.annotation.tailrec
    def lengthIter[A](list: List[A], length: Int): Int = {
      list match {
        case Nil => length
        case _ => lengthIter(list.tail, length + 1)
      }
    }

    lengthIter(list, 0)
  }

  //exercise 3
  //time complexity O(n+m), n = first.length, m = second.length
  //space complexity O(1)
  def append[A](first: List[A], second: List[A]): List[A] = {
    @scala.annotation.tailrec
    def appendIter[A](first: List[A], second: List[A], appendList: List[A]): List[A] = {
      (first, second) match {
        case (Nil, Nil) => rev(appendList)
        case (Nil, _) => rev(appendList) ::: second
        case (_, Nil) => rev(appendList) ::: first
        case (_, _) =>
          appendIter(first.tail, second.tail, second.head :: first.head :: appendList)
      }
    }

    appendIter(first, second, List())
  }

  //exercise 4
  //time complexity O(k(n+m)) , k = list.length, (n+m) because of usage of function containsPattern
  //space complexity O(k) - no tail recursion and k stack frames
  def find(list: List[String], pattern: String): List[String] = {
    if (pattern == Nil || list == Nil) Nil
    else if (containsPattern(list.head, pattern)) list.head :: find(list.tail, pattern)
    else find(list.tail, pattern)
  }

  //time complexity O(k(n+m)) , the same like find function
  //space complexity O(1) - tail recursion
  def findTail(list: List[String], pattern: String): List[String] = {
    @scala.annotation.tailrec
    def findIter(elements: List[String], pattern: String, resultList: List[String]): List[String] = {
      if (pattern == Nil || elements == Nil) rev(resultList)
      else if (containsPattern(elements.head, pattern)) findIter(elements.tail, pattern, elements.head :: resultList)
      else findIter(elements.tail, pattern, resultList)
    }

    findIter(list, pattern, List())
  }

  //time complexity O(n*m*s) - n = list.length, m = patternList.length, s - average length of String in list - comparing each element of list with whole patternList
  //space complexity O(n)
  def findN(list: List[String], patternList: List[String]): List[String] = {
    if (list == Nil) Nil
    else if (contains(list.head, patternList)) list.head :: findN(list.tail, patternList)
    else findN(list.tail, patternList)
  }

  //time complexity O(n*m*s)
  //space complexity O(1)
  def findNTail(list: List[String], patternList: List[String]): List[String] = {
    @scala.annotation.tailrec
    def findNIter(elements: List[String], patternList: List[String], resultList: List[String]): List[String] = {
      if (elements == Nil) rev(resultList)
      else if (contains(elements.head, patternList)) findNIter(elements.tail, patternList, elements.head :: resultList)
      else findNIter(elements.tail, patternList, resultList)
    }

    findNIter(list, patternList, List())
  }


  @scala.annotation.tailrec
  private def contains(element: String, pattern: List[String]): Boolean = {
    if (pattern == Nil) false
    else if (containsPattern(element, pattern.head)) true
    else contains(element, pattern.tail)
  }

  //time complexity O(n+m), n = element.length, m = pattern.length
  //worst case time complexity O(n*m) - rarely :)
  //space complexity O(1)
  private def containsPattern(element: String, pattern: String): Boolean = {
    @scala.annotation.tailrec
    def containsPatternHelper(element: List[Char], patternList: List[Char]): Boolean = {
      if (patternList == Nil) true
      else if (patternList != Nil && element == Nil) false
      else if (element.head == patternList.head) containsPatternHelper(element.tail, patternList.tail)
      else false
    }

    if (element == "") false
    else if (element.head == pattern.head && containsPatternHelper(element.tail.toList, pattern.tail.toList)) true
    else containsPattern(element.tail, pattern)

  }

  //exercise 5
  //time complexity O(n + m) , n = first.length, m = second.length
  //space complexity O(n+m)
  def joinLists[A](first: List[A], second: List[A], third: List[A]): List[A] = {
    (first, second, third) match {
      case (h :: t, _, _) => h :: joinLists(t, second, third)
      case (Nil, h :: t, _) => h :: joinLists(first, t, third)
      case (Nil, Nil, _) => third
    }
  }

  //time complexity O(n + m + k) , n = first.length, m = second.length, k = third.length
  //space complexity O(1)
  def joinListsTail[A](first: List[A], second: List[A], third: List[A]): List[A] = {
    @scala.annotation.tailrec
    def joinListsIter[A](first: List[A], second: List[A], third: List[A], result: List[A]): List[A] = {
      (first, second, third) match {
        case (h :: t, _, _) => joinListsIter(t, second, third, h :: result)
        case (Nil, h :: t, _) => joinListsIter(first, t, third, h :: result)
        case (Nil, Nil, h :: t) => joinListsIter(first, second, t, h :: result)
        case (Nil, Nil, Nil) => rev(result)
      }
    }

    joinListsIter(first, second, third, List())
  }

}
