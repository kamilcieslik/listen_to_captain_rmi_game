package rmi;

import java.io.Serializable;
import java.util.List;

public class SpaceCommand implements Serializable
{
    private String type;
    private List<Integer> parameters;

    public SpaceCommand(String type, List<Integer> parameters)
    {
        this.type = type;
        this.parameters = parameters;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public List<Integer> getParameters()
    {
        return parameters;
    }

    public void setParameters(List<Integer> parameters)
    {
        this.parameters = parameters;
    }

    /**
     * Verifies results submitted by player.
     *      max 15% error - 1 point
     *      max 10% error - 2 points
     * @param results of player's actions
     * @return amount of points earned (0 - 2);
     */
    public int verifyResults(List<Integer> results)
    {
        int score = 0;
        for(int i = 0; i < results.size(); i++)
        {
            int difference = parameters.get(i) - results.get(i);
            if(difference < 0)
                difference *= -1;

            if(difference <= 15)
            {
                if(difference <= 10)
                    score += 2;
                else
                    score += 1;

            }
        }
        return (int)((float)score / results.size());
    }

}
