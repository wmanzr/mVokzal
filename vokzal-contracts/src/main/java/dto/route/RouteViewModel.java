package dto.route;

public record RouteViewModel(
        Integer id,
        String timeDep,
        String timeArr,
        Integer depPlId,
        Integer arrPlId,
        boolean del
) {}