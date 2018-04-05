package model.relationship;

import java.util.Comparator;

/**
 * Created by alice on 4/4/2018.
 */
public class RegardHighToLow implements Comparator<IRelationship> {
  @Override
  public int compare(IRelationship o1, IRelationship o2) {
    return (int)(o2.regard() - o1.regard() * 10);
  }
}
