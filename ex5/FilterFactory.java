package filesprocessing;


/**
 * Factory class that represents a factory for Filter object.
 */
public class FilterFactory {


    /**
     * Method that receives a string input and returns the compatible filter object.
     * @param filterType The type of the filter.
     * @return The compatible filter.
     * @throws TypeOneError An error of type one.
     */
    public Filter getFilter(String filterType) throws TypeOneError {

        String[] filterInfo = filterType.split("#");

        if(filterInfo[0].equals("greater_than")){
            if(Double.parseDouble(filterInfo[1]) >= 0 ){
                return new GreaterThanFilter(Double.parseDouble(filterInfo[1]),filterInfo.length == 3);
            } else{
                throw new TypeOneBadFilterParamError();
            }
        }

        if(filterInfo[0].equals("between")){
            if((Double.parseDouble(filterInfo[1]) >= 0) &&
                    (Double.parseDouble(filterInfo[2]) >= 0) &&
                    (Double.parseDouble(filterInfo[1]) <= Double.parseDouble(filterInfo[2]))){
                return new BetweenFilter(Double.parseDouble(filterInfo[1]),
                        Double.parseDouble(filterInfo[2]), filterInfo.length == 4);
            } else{
                throw new TypeOneBadFilterParamError();
            }
        }

        if(filterInfo[0].equals("smaller_than")){
            if(Double.parseDouble(filterInfo[1]) >= 0 ){
                return new SmallerThanFilter(Double.parseDouble(filterInfo[1]),filterInfo.length == 3);
            } else{
                throw new TypeOneBadFilterParamError();
            }
        }

        if(filterInfo[0].equals("file")){
            return new FileFilter(filterInfo[1],filterInfo.length == 3);
        }

        if(filterInfo[0].equals("contains")){
            return new ContainsFilter(filterInfo[1],filterInfo.length == 3);
        }

        if(filterInfo[0].equals("prefix")){
            return new PrefixFilter(filterInfo[1], filterInfo.length == 3);
        }

        if(filterInfo[0].equals("suffix")){
            return new SuffixFilter(filterInfo[1], filterInfo.length == 3);
        }

        if(filterInfo[0].equals("writable")) {
            if (filterInfo[1].equals("YES") | filterInfo[1].equals("NO")) {
                return new WritableFilter(filterInfo[1], filterInfo.length == 3);
            } else{
                throw new TypeOneBadFilterParamError();
            }
        }

        if(filterInfo[0].equals("executable")){
            if (filterInfo[1].equals("YES") | filterInfo[1].equals("NO")) {
                return new ExecutableFilter(filterInfo[1], filterInfo.length == 3);
            } else{
                throw new TypeOneBadFilterParamError();
            }
        }

        if(filterInfo[0].equals("hidden")){
            if(filterInfo[1].equals("YES") | filterInfo[1].equals("NO")){
                return new HiddenFilter(filterInfo[1], filterInfo.length == 3);
            } else{
                throw new TypeOneBadFilterParamError();
            }
        }

        if(filterInfo[0].equals("all")){
            return new AllFilter(filterInfo.length == 2);
        } else{
            throw new TypeOneBadFilterNameError();
        }
    }
}
