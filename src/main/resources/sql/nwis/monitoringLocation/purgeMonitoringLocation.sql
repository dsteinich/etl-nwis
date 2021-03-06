delete from nwis.monitoring_location
 where not exists (select null
                     from nwis.sitefile
                    where monitoring_location.agency_cd = sitefile.agency_cd and
                          monitoring_location.site_identification_number = sitefile.site_no and
                          monitoring_location.site_id = sitefile.site_id and
                          sitefile.dec_lat_va <> 0 and
                          sitefile.dec_long_va <> 0 and
                          sitefile.site_web_cd = 'Y' and
                          sitefile.db_no = '01' and
                          sitefile.site_tp_cd not in ('FA-WTP', 'FA-WWTP', 'FA-TEP', 'FA-HP') and
                          sitefile.nwis_host not in ('fltlhsr001', 'fltpasr001', 'flalssr003') and
                          sitefile.country_cd != 'CN')
