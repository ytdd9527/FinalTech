package io.taraxacum.finaltech.util;

import io.taraxacum.finaltech.util.cargo.PositionInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 */
public class PositionHelper {
    private PositionStats[] positionStats = new PositionStats[6];
    public static class PositionStats{
        // up;down;east;south;west;north
        private String position;

        // input;output
        private String type;

        PositionStats(String position, String type) {
            this.position = position;
            this.type = type;
        }
    }

    public PositionHelper(String positions) {
        for(String position : positions.split("-")) {
            String[] info = position.split(":");
            if(info != null && info.length == 3 && !info[1].equals(PositionInfo.POSITION_TYPE_NULL)) {
                positionStats[Integer.parseInt(info[0]) - 1] = new PositionStats(info[2], info[1]);
            }
        }
    }

    @Override
    public String toString() {
        String s = "";
        for(int i = 0; i < positionStats.length; i++) {
            if (positionStats[i] == null || positionStats[i].type.equals(PositionInfo.POSITION_TYPE_NULL)) {
                continue;
            }
            s += (i+1);
            s += ":";
            s += positionStats[i].type;
            s += ":";
            s += positionStats[i].position;
            s += "-";
        }
        return s;
    }

    public int getOrder(String position) {
        for(int i = 0; i < positionStats.length; i++) {
            if(positionStats[i] != null && positionStats[i].position.equals(position) && !positionStats[i].type.equals(PositionInfo.POSITION_TYPE_NULL)) {
                return i+1;
            }
        }
        return -1;
    }

    public String[] getInputs() {
        List<String> inputs = new ArrayList<>();
        for(PositionStats positionStats : positionStats) {
            if (positionStats != null && (positionStats.type.equals(PositionInfo.POSITION_TYPE_INPUT) || positionStats.type.equals(PositionInfo.POSITION_TYPE_IN_AND_OUT))) {
                inputs.add(positionStats.position);
            }
        }
        String[] result = new String[inputs.size()];
        for(int i = 0; i < result.length; i++) {
            result[i] = inputs.get(i);
        }
        return result;
    }

    public String[] getOutputs() {
        List<String> outputs = new ArrayList<>();
        for(PositionStats positionStats : positionStats) {
            if (positionStats != null && (positionStats.type.equals(PositionInfo.POSITION_TYPE_OUTPUT) || positionStats.type.equals(PositionInfo.POSITION_TYPE_IN_AND_OUT))) {
                outputs.add(positionStats.position);
            }
        }
        String[] result = new String[outputs.size()];
        for(int i = 0; i < result.length; i++) {
            result[i] = outputs.get(i);
        }
        return result;
    }

    public String getType(String position) {
        for(PositionStats positionStats : positionStats) {
            if(positionStats != null && positionStats.position.equals(position)) {
                return positionStats.type;
            }
        }
        return PositionInfo.POSITION_TYPE_NULL;
    }

    public void setType(String position, String type) {
        for(PositionStats positionStats : positionStats) {
            if(positionStats != null && positionStats.position.equals(position)) {
                positionStats.type = type;
                return;
            }
        }
    }

    public String nextType(String position) {
        for(int i = 0; i < this.positionStats.length; i++) {
            PositionStats positionStats = this.positionStats[i];
            if(positionStats != null && positionStats.position.equals(position)) {
                switch (positionStats.type) {
                    case PositionInfo.POSITION_TYPE_NULL:
                        positionStats.type = PositionInfo.POSITION_TYPE_INPUT;
                        return PositionInfo.POSITION_TYPE_INPUT;
                    case PositionInfo.POSITION_TYPE_INPUT:
                        positionStats.type = PositionInfo.POSITION_TYPE_OUTPUT;
                        return PositionInfo.POSITION_TYPE_OUTPUT;
                    case PositionInfo.POSITION_TYPE_OUTPUT:
                        positionStats.type = PositionInfo.POSITION_TYPE_IN_AND_OUT;
                        return PositionInfo.POSITION_TYPE_IN_AND_OUT;
                    case PositionInfo.POSITION_TYPE_IN_AND_OUT:
                    default:
                        positionStats.type = PositionInfo.POSITION_TYPE_NULL;
                        return PositionInfo.POSITION_TYPE_NULL;
                }
            }
        }
        int i = getFirstNull();
        if(i != -1) {
            positionStats[i-1] = new PositionStats(position, PositionInfo.POSITION_TYPE_INPUT);
        }
        return PositionInfo.POSITION_TYPE_INPUT;
    }

    public int getFirstNull() {
        for(int i = 0; i < positionStats.length; i++) {
            if(positionStats[i] == null || positionStats[i].type.equals(PositionInfo.POSITION_TYPE_NULL)) {
                return i+1;
            }
        }
        return -1;
    }
}
